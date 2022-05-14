package com.perseus.task.validator.impl;

import com.perseus.task.enums.ContactType;
import com.perseus.task.payload.request.UserUpdateContactRequest;
import com.perseus.task.util.HelperMethod;
import com.perseus.task.validator.annotation.ValidUpdateContact;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateUpdateContactRequest implements ConstraintValidator<ValidUpdateContact, UserUpdateContactRequest> {
    @Override
    public void initialize(ValidUpdateContact constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateContactRequest req, ConstraintValidatorContext constraintValidatorContext) {
        ContactType contactType = HelperMethod.filterEnum(ContactType.class, req.getContactType());

        var validateOldEmail = HelperMethod.validateEmail(req.getOldEmailContact());
        var validateNewEmail = HelperMethod.validateEmail(req.getNewEmailContact());

        // if user choose email and enter phoneNumber then fail
        if (contactType == ContactType.EMAIL && !validateOldEmail && !validateNewEmail) return false;

        // if user choose phone number and enter email then fail
        return contactType != ContactType.PHONE_NUMBER || !validateOldEmail;
    }
}
