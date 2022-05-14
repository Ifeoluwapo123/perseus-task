package com.perseus.task.validator.impl;

import com.perseus.task.enums.ContactType;
import com.perseus.task.payload.request.UserContactRequest;
import com.perseus.task.util.HelperMethod;
import com.perseus.task.validator.annotation.ValidContact;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateContactRequest implements ConstraintValidator<ValidContact, UserContactRequest> {
    @Override
    public void initialize(ValidContact constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserContactRequest req, ConstraintValidatorContext constraintValidatorContext) {
        var contactType = HelperMethod.filterEnum(ContactType.class, req.getContactType());

        var validateEmail = HelperMethod.validateEmail(req.getEmailContact());
        // if user choose email and enter phoneNumber then fail
        if (contactType == ContactType.EMAIL && !validateEmail) return false;

        // if user choose phone number and enter email then fail
        return contactType != ContactType.PHONE_NUMBER || !validateEmail;
    }
}
