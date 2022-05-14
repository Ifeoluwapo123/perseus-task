package com.perseus.task.validator.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateContactTypeTest {

    @Test
    void isValid() {
        ValidateContactType contactType = new ValidateContactType();

        // invalid req parameter for contact-type
        assertFalse(contactType.isValid("emails", null));
        assertFalse(contactType.isValid("emailing", null));
        assertFalse(contactType.isValid("phoneNumbers", null));
        assertFalse(contactType.isValid("phone numbers", null));
        // valid ones
        assertTrue(contactType.isValid("email", null));
        assertTrue(contactType.isValid("phone Number", null));
        assertTrue(contactType.isValid("phone number", null));
    }
}