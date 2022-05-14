package com.perseus.task.service.impl;

import com.perseus.task.exception.ApiConflictException;
import com.perseus.task.exception.ApiResourceNotFoundException;
import com.perseus.task.model.Email;
import com.perseus.task.model.PhoneNumber;
import com.perseus.task.model.User;
import com.perseus.task.payload.request.UserContactRequest;
import com.perseus.task.payload.request.UserCreationRequest;
import com.perseus.task.payload.request.UserUpdateContactRequest;
import com.perseus.task.payload.response.MessageResponse;
import com.perseus.task.repository.EmailRepository;
import com.perseus.task.repository.PhoneNumberRepository;
import com.perseus.task.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailRepository emailRepository;
    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    private UserServiceImpl userService;
    private UserCreationRequest request;
    private User user;

    @BeforeEach
    void setUp() {
        // request
        request = new UserCreationRequest();
        request.setEmail("testing@gmail.com");
        request.setPhoneNumber("09100987654");
        request.setFirstName("firstname");
        request.setLastName("lastName");

        userService = new UserServiceImpl(userRepository, emailRepository, phoneNumberRepository);

         Email email = Email.builder()
                .email("testing@gmail.com")
                .build();

        PhoneNumber phoneNumber = PhoneNumber.builder()
                .phoneNumber("09100987654")
                .build();

        user = User.builder()
                .firstName("testing")
                .lastName("lastName")
                .emails(Collections.singletonList(email))
                .phoneNumbers(Collections.singletonList(phoneNumber))
                .build();
    }

    @Test
    void shouldSuccessfullyCreateUser() {
        // given
        given(emailRepository.existsByEmail(request.getEmail())).willReturn(false);

        // when
        var res = userService.createUser(request);

        // then
        ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);
        verify(emailRepository).save(emailArgumentCaptor.capture());
        ArgumentCaptor<PhoneNumber> phoneNumberArgumentCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
        verify(phoneNumberRepository).save(phoneNumberArgumentCaptor.capture());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        assertTrue(res.getMessage().equalsIgnoreCase("Successfully created user"));
        assertEquals(res.getClass(), MessageResponse.class);
    }

    @Test
    void shouldThrowExceptionBecauseEmailExist(){
        // given
        given(emailRepository.existsByEmail(request.getEmail())).willReturn(true);

        // then
        assertThatThrownBy(()-> userService.createUser(request))
                .isInstanceOf(ApiConflictException.class)
                .hasMessageContaining("User already exist");

        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserByIdShouldBeSuccessful() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(user));

        // when
        var res = userService.getUserById(1L);

        // then
        assertEquals(user, res);
    }

    @Test
    void getUserByIdShouldThrowException(){
        assertThatThrownBy(()-> userService.getUserById(1L))
                .isInstanceOf(ApiResourceNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void userAddContactEmail() {
        UserContactRequest req = new UserContactRequest();
        req.setContactType("EMAIL");
        req.setEmailContact("test@gmail.com");
        req.setUserId(1L);
        // given
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(user));

        // when
        var res = userService.userAddContact(req);

        // then
        ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);
        verify(emailRepository).save(emailArgumentCaptor.capture());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        assertEquals(user.getEmails().size(), 2);
        assertEquals(res.getClass(), MessageResponse.class);
    }

    @Test
    void userAddContactPhoneNumber() {
        UserContactRequest req = new UserContactRequest();
        req.setContactType("PHONE_NUMBER");
        req.setEmailContact("09100763542");
        req.setUserId(1L);
        // given
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(user));

        // when
        var res = userService.userAddContact(req);

        // then
        ArgumentCaptor<PhoneNumber> phoneNumberArgumentCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
        verify(phoneNumberRepository).save(phoneNumberArgumentCaptor.capture());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        assertEquals(user.getPhoneNumbers().size(), 2);
        assertEquals(res.getClass(), MessageResponse.class);
    }

    @Test
    void userAddContactPhoneNumberShowThrowExceptionUserNotFound(){
        UserContactRequest req = new UserContactRequest();
        req.setContactType("PHONE_NUMBER");
        req.setEmailContact("09100763542");
        req.setUserId(1L);

        assertThatThrownBy(()-> userService.userAddContact(req))
                .isInstanceOf(ApiResourceNotFoundException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, never()).save(any());
    }

    @Test
    void userAddContactEmailShowThrowExceptionUserNotFound(){
        UserContactRequest req = new UserContactRequest();
        req.setContactType("EMAIL");
        req.setEmailContact("test@gmail.com");
        req.setUserId(1L);

        assertThatThrownBy(()-> userService.userAddContact(req))
                .isInstanceOf(ApiResourceNotFoundException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, never()).save(any());
    }

    @Test
    void userUpdateEmailContact() {
        UserUpdateContactRequest req = new UserUpdateContactRequest();
        req.setContactType("EMAIL");
        req.setOldEmailContact("testing@gmail.com");
        req.setNewEmailContact("testOne@gmail.com");
        req.setUserId(1L);

        // given
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(user));
        given(emailRepository.existsByEmail(req.getOldEmailContact())).willReturn(true);

        // when
        var resEmail = userService.userUpdateContact(req);

        // then
        ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);
        // save was called to update email
        verify(emailRepository).save(emailArgumentCaptor.capture());
        assertEquals(user.getEmails().size(), 1);
        assertEquals(resEmail.getClass(), MessageResponse.class);
        verify(phoneNumberRepository, never()).save(any());
    }

    @Test
    void userUpdatePhoneNumber(){
        UserUpdateContactRequest req = new UserUpdateContactRequest();
        req.setContactType("PHONE_NUMBER");
        req.setOldPhoneNumber("09100987654");
        req.setNewPhoneNumber("09087654332");
        req.setUserId(1L);

        // given
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(user));
        given(phoneNumberRepository.existsByPhoneNumber(req.getOldPhoneNumber()))
                .willReturn(true);
        // when
        var resPhoneNumber = userService.userUpdateContact(req);

        // then
        ArgumentCaptor<PhoneNumber> phoneNumberArgumentCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
        // save method was called to update phoneNumber
        verify(phoneNumberRepository).save(phoneNumberArgumentCaptor.capture());
        assertEquals(user.getPhoneNumbers().size(), 1);
        assertEquals(resPhoneNumber.getClass(), MessageResponse.class);
        verify(emailRepository, never()).save(any());
    }

    @Test
    void deleteUser() {
    }
}