package com.perseus.task.util;

import com.perseus.task.enums.ContactType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelperMethodTest {

    @Test
    void filterEnum() {
        assertEquals(HelperMethod.filterEnum(ContactType.class, "email"), ContactType.EMAIL);
        assertEquals(HelperMethod.filterEnum(ContactType.class, "phone number"), ContactType.PHONE_NUMBER);
    }

    @Test
    void filterEnumCaseInsensitive(){
        assertEquals(HelperMethod.filterEnum(ContactType.class, "EMAIL"), ContactType.EMAIL);
        assertEquals(HelperMethod.filterEnum(ContactType.class, "EmAiL"), ContactType.EMAIL);
        assertEquals(HelperMethod.filterEnum(ContactType.class, "pHone Number"), ContactType.PHONE_NUMBER);
    }

    @Test
    void filterEnumReturnNullForInconsistentData(){
        assertNull(HelperMethod.filterEnum(ContactType.class, "emailing"));
        assertNull(HelperMethod.filterEnum(ContactType.class, "phone numbers"));
    }

    @Test
    void validateEmail(){
        // simple email validation
        assertTrue(HelperMethod.validateEmail("testing@gmail.com"));
        assertTrue(HelperMethod.validateEmail("Test@gmail.co"));
        assertFalse(HelperMethod.validateEmail("testing.com"));
        assertFalse(HelperMethod.validateEmail("testing@"));
    }
}