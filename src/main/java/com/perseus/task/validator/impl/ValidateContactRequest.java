package com.perseus.task.validator.impl;

import com.perseus.task.enums.ContactType;
import com.perseus.task.payload.request.UserContactRequest;
import com.perseus.task.util.HelperMethod;
import com.perseus.task.validator.annotation.ValidContact;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ValidateContactRequest implements ConstraintValidator<ValidContact, UserContactRequest> {
    @Override
    public void initialize(ValidContact constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserContactRequest req, ConstraintValidatorContext constraintValidatorContext) {
        var contactType = HelperMethod.filterEnum(ContactType.class, req.getContactType());
        if(contactType == null) return false;

        if(Objects.equals(contactType, ContactType.EMAIL)){
            var email = req.getEmail();
            if(email == null) return false;
            return !email.equals("") && HelperMethod.validateEmail(email);
        }else{
            var phoneNumber = req.getPhoneNumber();
            if(phoneNumber == null) return false;
            if(HelperMethod.validateEmail(phoneNumber)) return false;
            return !phoneNumber.equals("");
        }
    }
}
