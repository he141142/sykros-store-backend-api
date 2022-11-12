package com.example.sykrosstore.internal.decoreators.constraint;

import com.example.sykrosstore.constants.enums.CaseMode;
import com.example.sykrosstore.internal.decoreators.interfaces.IsEntityExist;
import com.example.sykrosstore.internal.decoreators.interfaces.UniqueAccount;
import com.example.sykrosstore.internal.repositories.BookRepositories;
import com.example.sykrosstore.internal.services.BookService;
import com.example.sykrosstore.internal.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsExist implements ConstraintValidator<IsEntityExist, Long> {

    @Autowired
    private IBookService bookService;

    ConstraintValidatorContext context;


    private CaseMode caseMode;

    @Override
    public void initialize(IsEntityExist constraintAnnotation) {
        this.caseMode = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        this.context = context;
        boolean isValid;
        System.out.println("Entry IsExist validator");
        switch (this.caseMode) {
            case BOOK:
                isValid = this.bookService.isBookExist(id);
                if (!isValid) context = this.violation("book","Book doesn't Exist");
                return isValid;
            case SUBGENRES:
                isValid = this.bookService.isSubGenresExist(id);
                if (!isValid) context = this.violation("SubGenres","SubGenres doesn't Exist");
                return isValid;
        }
        return false;
    }


    private ConstraintValidatorContext violation(String node,String __msg){
            this.context.buildConstraintViolationWithTemplate(__msg)
                    .addPropertyNode(node)
                    .addConstraintViolation();
            return this.context;
    }


}
