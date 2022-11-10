package com.example.sykrosstore.internal.decoreators.constraint;

import com.example.sykrosstore.internal.decoreators.interfaces.UniqueAccount;
import com.example.sykrosstore.internal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class UniqueLoginValidator implements ConstraintValidator<UniqueAccount,String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueAccount constraintAnnotation) {
    }

    @Override
    public boolean isValid(String accountName, ConstraintValidatorContext context) {
        System.out.println("entry validator");
        System.out.println(accountName);
        System.out.println(userRepository.existsByUserName(accountName));
        System.out.println(userRepository.findFirstByUserName(accountName).orElse(null));
        return accountName != null && !userRepository.existsByUserName(accountName);
    }
}
