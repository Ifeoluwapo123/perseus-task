package com.perseus.task.service.impl;

import com.perseus.task.enums.ContactType;
import com.perseus.task.exception.ApiBadRequestException;
import com.perseus.task.exception.ApiConflictException;
import com.perseus.task.exception.ApiResourceNotFoundException;
import com.perseus.task.model.Email;
import com.perseus.task.model.PhoneNumber;
import com.perseus.task.model.User;
import com.perseus.task.payload.request.UserAddContactRequest;
import com.perseus.task.payload.request.UserCreationRequest;
import com.perseus.task.payload.response.MessageResponse;
import com.perseus.task.repository.EmailRepository;
import com.perseus.task.repository.PhoneNumberRepository;
import com.perseus.task.repository.UserRepository;
import com.perseus.task.service.UserService;
import com.perseus.task.util.HelperMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResponse createUser(UserCreationRequest req) {
        //check if user does not exist before
        if(userExist(req.getEmail())) throw new ApiConflictException("User already exist");

        // user contact -> email
        Email email = buildEmailContact(req.getEmail());
        var userEmail = emailRepository.saveAndFlush(email);

        // user contact -> phone number
        PhoneNumber phoneNumber = buildPhoneNumberContact(req.getPhoneNumber());
        var userPhoneNumber = phoneNumberRepository.saveAndFlush(phoneNumber);

        List<PhoneNumber> userPhoneNumberList = Collections.singletonList(userPhoneNumber);
        List<Email> userEmailList = Collections.singletonList(userEmail);

        User user = buildUserObj(req.getFirstName(), req.getLastName(), userEmailList, userPhoneNumberList);
        userRepository.save(user);

        return new MessageResponse("Successfully created user");
    }

    @Override
    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new ApiResourceNotFoundException("User not found"));
    }

    @Override
    public Page<User> getUserByName(String name, Pageable pageable){
        return userRepository.searchUserByName(name,pageable);
    }

    public MessageResponse userAddContact(UserAddContactRequest req){
        var contact = HelperMethod.filterEnum(ContactType.class, req.getContactType());
        if(contact == null) throw new ApiBadRequestException("Please enter a valid contact type");

        if(contact.equals(ContactType.EMAIL)){

        }
        return null;
    }

    private boolean userExist(String email){
        return emailRepository.existsByEmail(email);
    }

    private Email buildEmailContact(String email){
        return Email.builder()
                .email(email)
                .build();
    }

    private PhoneNumber buildPhoneNumberContact(String phoneNumber){
        return PhoneNumber.builder()
                .phoneNumber(phoneNumber)
                .build();
    }

    private User buildUserObj(String firstName, String lastName, List<Email> emails, List<PhoneNumber> phoneNumbers){
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .build();
    }
}
