package com.perseus.task.validator.annotation;

import com.perseus.task.validator.impl.ValidateContactType;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateContactType.class)
public @interface ValidContactType {
    String message() default "Please enter valid contact type(email or phoneNumber)";
    Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
