package com.perseus.task.validator.impl;

import com.perseus.task.payload.request.UserContactRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateContactRequestTest {

    private final ValidateContactRequest req = new ValidateContactRequest();
    private final UserContactRequest request = new UserContactRequest();

    @BeforeEach
    void setUp() {
        request.setUserId(1L);
    }

    @Test
    void isValidEmail() {
        // for email
        request.setContactType("email");
        request.setEmail("test@gmail.com");

        assertTrue(req.isValid(request, null));
    }

    @Test
    void isValidPhoneNumber(){
        // for phone number
        request.setContactType("phone number");
        request.setPhoneNumber("09100827653");

        assertTrue(req.isValid(request, null));
    }

    @Test
    void isNotValidBecauseEmailContactTypeWasSelectedButPhoneNumberWasSupplied(){
        request.setContactType("email");
        request.setEmail("09100876254");

        assertFalse(req.isValid(request, null));
    }

    @Test
    void isNotValidBecausePhoneNumberContactTypeWasSelectedButEmailWasSupplied(){
        request.setContactType("phone number");
        request.setEmail("testing@gmail.com");

        assertFalse(req.isValid(request, null));
    }
}