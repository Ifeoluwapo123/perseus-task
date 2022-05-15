package com.perseus.task.service.impl;

import com.perseus.task.enums.ContactType;
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
import com.perseus.task.service.UserService;
import com.perseus.task.util.HelperMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    @Transactional()
    public MessageResponse createUser(UserCreationRequest req) {
        //check if user does not exist before
        if(emailRepository.existsByEmail(req.getEmail())) throw new ApiConflictException("User already exist");

        // user contact -> email
        Email email = buildEmailContact(req.getEmail());
        var userEmail = emailRepository.save(email);

        // user contact -> phone number
        PhoneNumber phoneNumber = buildPhoneNumberContact(req.getPhoneNumber());
        var userPhoneNumber = phoneNumberRepository.save(phoneNumber);

        List<PhoneNumber> userPhoneNumberList = new ArrayList<>();
        userPhoneNumberList.add(userPhoneNumber);
        List<Email> userEmailList = new ArrayList<>();
        userEmailList.add(userEmail);

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
        return userRepository.searchUserByName(name, pageable);
    }

    @Override
    @Transactional()
    public MessageResponse userAddContact(UserContactRequest req){
        User user = getUserById(req.getUserId());
        ContactType contact = HelperMethod.filterEnum(ContactType.class, req.getContactType());

        if(Objects.equals(contact, ContactType.EMAIL)){
            if(emailRepository.existsByEmail(req.getEmail()))
                throw new ApiResourceNotFoundException("Email already exist");
            // save new email
            Email email = Email.builder().email(req.getEmail()).build();
            Email savedEmail = emailRepository.save(email);

            // add the new email to the list of emails
            List<Email> userEmails = new ArrayList<>(user.getEmails());
            userEmails.add(savedEmail);

            // set emails
            user.setEmails(userEmails);
        }else{
            // save phone number
            PhoneNumber phoneNumber = PhoneNumber.builder().phoneNumber(req.getPhoneNumber()).build();
            PhoneNumber savedPhoneNumber = phoneNumberRepository.save(phoneNumber);

            // add the new phone number to the list of phone numbers
            List<PhoneNumber> userPhoneNumbers = new ArrayList<>(user.getPhoneNumbers());
            userPhoneNumbers.add(savedPhoneNumber);

            // set phone numbers
            user.setPhoneNumbers(userPhoneNumbers);
        }
        // save user
        userRepository.save(user);

        return new MessageResponse("Successfully added new Contact");
    }

    @Override
    @Transactional()
    public MessageResponse userUpdateContact(UserUpdateContactRequest req){
        User user = getUserById(req.getUserId());
        ContactType contact = HelperMethod.filterEnum(ContactType.class, req.getContactType());

        if(Objects.equals(contact, ContactType.EMAIL)){
            if(!emailRepository.existsByEmail(req.getOldEmailContact()))
                throw new ApiResourceNotFoundException("Old Email Not found");

            if(emailRepository.existsByEmail(req.getNewEmailContact()))
                throw new ApiResourceNotFoundException("New Email already exist");

            List<Email> updatedUserEmailList = user.getEmails().stream()
                .map(email-> updateEmail(email, req)).collect(Collectors.toList());

            user.setEmails(updatedUserEmailList);
        }else{
            if(!phoneNumberRepository.existsByPhoneNumber(req.getOldPhoneNumber()))
                throw new ApiResourceNotFoundException("Phone Number Not found");

            List<PhoneNumber> updatedUserPhoneNumberList = user.getPhoneNumbers().stream()
                .map(phoneNumber-> updatePhoneNumber(phoneNumber, req)).collect(Collectors.toList());

            user.setPhoneNumbers(updatedUserPhoneNumberList);
        }
        // save user
        userRepository.save(user);

        return new MessageResponse("Successfully updated contact");
    }

    @Override
    public MessageResponse deleteUser(Long userId){
        userRepository.deleteById(userId);
        return new MessageResponse("Successfully deleted user id "+userId);
    }

    private Email updateEmail(Email email, UserUpdateContactRequest req){
        if(email.getEmail().equals(req.getOldEmailContact())){
            email.setEmail(req.getNewEmailContact());
            return emailRepository.save(email);
        }

        return email;
    }

    private PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber, UserUpdateContactRequest req){
        if(phoneNumber.getPhoneNumber().equals(req.getOldPhoneNumber())){
            phoneNumber.setPhoneNumber(req.getNewPhoneNumber());
            return phoneNumberRepository.save(phoneNumber);
        }

        return phoneNumber;
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
