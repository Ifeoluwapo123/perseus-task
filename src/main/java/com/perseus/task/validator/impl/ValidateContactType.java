package com.perseus.task.validator.impl;

import com.perseus.task.enums.ContactType;
import com.perseus.task.util.HelperMethod;
import com.perseus.task.validator.annotation.ValidContactType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateContactType implements ConstraintValidator<ValidContactType, String> {
    @Override
    public void initialize(ValidContactType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return HelperMethod.filterEnum(ContactType.class, s) != null;
    }
}
