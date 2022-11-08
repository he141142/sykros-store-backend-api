package com.example.sykrosstore.constants.common.controller.advice;

import com.example.sykrosstore.constants.common.err.ErrorDetail;
import com.example.sykrosstore.custom.validation.ValidationConstraint;
import com.example.sykrosstore.custom.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobleExceptionHandler {

    public GlobleExceptionHandler() {
    }


    @ExceptionHandler({FileNotFoundException.class, ParseException.class, EntityException.class, UpdateException.class})
    public ResponseEntity<?> resourceNotFoundExcp(Exception ex, WebRequest request) {
        CustomExcp customExcp = new CustomExcp.Builder().setStatus(HttpStatus.NOT_FOUND).setErr(
                new ErrorDetail(ex.getMessage(), request.getDescription(false))
        ).build();
        System.out.println(ex.getMessage());
        return customExcp.getResponseExp(ex, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<ValidationConstraint> onMethodArgumentInvalid(ConstraintViolationException e){
        List<String> listViolations = new ArrayList<>();
        ValidationConstraint validationConstraint = new ValidationConstraint();
        for (ConstraintViolation violation : e.getConstraintViolations()){
            validationConstraint.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(),violation.getMessage())
            );
        }
        return new ResponseEntity<>(validationConstraint,HttpStatus.BAD_REQUEST);
    }
}
