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
    void isValid() {
        // for email
        request.setContactType("email");
        request.setEmailContact("test@gmail.com");

        assertTrue(req.isValid(request, null));

        // for phone number
        request.setContactType("phone number");
        request.setEmailContact("09100827653");

        assertTrue(req.isValid(request, null));
    }

    @Test
    void isNotValidBecauseEmailContactTypeWasSelectedButPhoneNumberWasSupplied(){
        request.setContactType("email");
        request.setEmailContact("09100876254");

        assertFalse(req.isValid(request, null));
    }

    @Test
    void isNotValidBecausePhoneNumberContactTypeWasSelectedButEmailWasSupplied(){
        request.setContactType("phone number");
        request.setEmailContact("testing@gmail.com");

        assertFalse(req.isValid(request, null));
    }
}