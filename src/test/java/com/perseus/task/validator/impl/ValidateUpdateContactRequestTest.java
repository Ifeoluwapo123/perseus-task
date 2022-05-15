package com.perseus.task.validator.impl;

import com.perseus.task.payload.request.UserUpdateContactRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidateUpdateContactRequestTest {

    private final ValidateUpdateContactRequest req = new ValidateUpdateContactRequest();
    private final UserUpdateContactRequest request = new UserUpdateContactRequest();

    @BeforeEach
    void setUp() {
        request.setUserId(1L);
    }

    @Test
    void isValidEmail() {
        // for email
        request.setContactType("email");
        request.setOldEmailContact("test@gmail.com");
        request.setNewEmailContact("test2@gmail.com");

        assertTrue(req.isValid(request, null));
    }

    @Test
    void isValidPhoneNumber(){
        // for phone number
        request.setContactType("phone number");
        request.setOldPhoneNumber("09100827653");
        request.setNewPhoneNumber("08100726536");

        assertTrue(req.isValid(request, null));
    }

    @Test
    void isNotValidBecauseEmailContactTypeWasSelectedButPhoneNumberWasSupplied(){
        request.setContactType("email");
        request.setOldEmailContact("test@gmail.com");
        request.setNewEmailContact("08023422455");

        assertFalse(req.isValid(request, null));
    }

    @Test
    void isNotValidBecausePhoneNumberContactTypeWasSelectedButEmailWasSupplied(){
        request.setContactType("phone number");
        request.setNewPhoneNumber("testing@gmail.com");
        request.setOldPhoneNumber("09182736444");

        assertFalse(req.isValid(request, null));
    }
}