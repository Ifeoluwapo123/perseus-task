package com.perseus.task.validator.impl;

import com.perseus.task.enums.ContactType;
import com.perseus.task.payload.request.UserUpdateContactRequest;
import com.perseus.task.util.HelperMethod;
import com.perseus.task.validator.annotation.ValidUpdateContact;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ValidateUpdateContactRequest implements ConstraintValidator<ValidUpdateContact, UserUpdateContactRequest> {
    @Override
    public void initialize(ValidUpdateContact constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateContactRequest req, ConstraintValidatorContext constraintValidatorContext) {
        ContactType contactType = HelperMethod.filterEnum(ContactType.class, req.getContactType());
        if(contactType == null) return false;

        if(Objects.equals(contactType, ContactType.EMAIL)){
            var oldEmail = req.getOldEmailContact();
            var  newEmail = req.getNewEmailContact();
            var validateOldEmail = HelperMethod.validateEmail(oldEmail);
            var validateNewEmail = HelperMethod.validateEmail(newEmail);
            if(oldEmail == null || newEmail == null) return false;
            if(oldEmail.equals("") || newEmail.equals("")) return false;
            return validateNewEmail && validateOldEmail;
        }else{
            var oldPhoneNumber = req.getOldPhoneNumber();
            var  newPhoneNumber = req.getNewPhoneNumber();

            if(oldPhoneNumber == null || newPhoneNumber == null) return false;

            var validateOldPhoneNumber = HelperMethod.validateEmail(oldPhoneNumber);
            var validateNewPhoneNumber = HelperMethod.validateEmail(newPhoneNumber);

            if(validateNewPhoneNumber || validateOldPhoneNumber) return false;

            return !oldPhoneNumber.equals("") && !newPhoneNumber.equals("");
        }
    }
}
