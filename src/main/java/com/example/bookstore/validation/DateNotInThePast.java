package com.example.bookstore.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {DateNotInThePastValidator.class})
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface DateNotInThePast {
    String message() default "The date should not be in the past";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}