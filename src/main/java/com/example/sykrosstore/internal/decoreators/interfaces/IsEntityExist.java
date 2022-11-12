package com.example.sykrosstore.internal.decoreators.interfaces;


import com.example.sykrosstore.constants.enums.CaseMode;
import com.example.sykrosstore.internal.decoreators.constraint.IsExist;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsExist.class)
public @interface IsEntityExist {
    CaseMode entity();
    boolean checkMode() default true;
    String message() default "User Name already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
