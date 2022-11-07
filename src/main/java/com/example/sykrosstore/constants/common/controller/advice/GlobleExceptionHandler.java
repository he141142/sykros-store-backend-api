package com.example.sykrosstore.constants.common.controller.advice;

import com.example.sykrosstore.constants.common.err.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;
import java.text.ParseException;

@ControllerAdvice
public class GlobleExceptionHandler {

    public GlobleExceptionHandler() {
    }


    @ExceptionHandler({FileNotFoundException.class, ParseException.class, EntityException.class})
    public ResponseEntity<?> resourceNotFoundExcp(Exception ex, WebRequest request) {
        CustomExcp customExcp = new CustomExcp.Builder().setStatus(HttpStatus.NOT_FOUND).setErr(
                new ErrorDetail(ex.getMessage(), request.getDescription(false))
        ).build();
        System.out.println("sttus code");
        System.out.println( customExcp.getResponseExp(ex,request).getStatusCode().value());

        System.out.println(ex.getMessage());
        return customExcp.getResponseExp(ex,request);
    }




}
