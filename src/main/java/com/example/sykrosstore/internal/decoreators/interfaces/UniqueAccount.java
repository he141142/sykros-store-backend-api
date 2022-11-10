package com.example.sykrosstore.internal.decoreators.interfaces;

import com.example.sykrosstore.internal.decoreators.constraint.UniqueLoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueLoginValidator.class)
public @interface UniqueAccount {
    String message() default "{err}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
