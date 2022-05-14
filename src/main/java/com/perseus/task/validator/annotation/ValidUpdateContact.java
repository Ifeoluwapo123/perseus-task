package com.perseus.task.validator.annotation;

import com.perseus.task.validator.impl.ValidateUpdateContactRequest;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateUpdateContactRequest.class)
public @interface ValidUpdateContact {
    String message() default "Contact-type does not match the contact provided";
    Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
