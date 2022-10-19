package com.example.sykrosstore.common.controller.advice;

import com.example.sykrosstore.common.err.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;

@ControllerAdvice
public class GlobleExceptionHandler {

    public GlobleExceptionHandler() {
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExceptionHandler(Exception ex, WebRequest request) {
        IGlobalExcp customExcp = new CustomExcp.Builder().customError(
                new ErrorDetail.Builder().build(ex.getMessage(), request.getDescription(false))
                , HttpStatus.INTERNAL_SERVER_ERROR).build();
        return customExcp.getResponseExp();
    }

    @ExceptionHandler({FileNotFoundException.class, ParseException.class})
    public ResponseEntity<?> resourceNotFoundExcp(Exception ex, WebRequest request) {
        IGlobalExcp customExcp = new CustomExcp.Builder().setStatus(HttpStatus.NOT_FOUND).setErr(
                new ErrorDetail(ex.getMessage(), request.getDescription(false))
        ).build();
        return customExcp.getResponseExp();
    }


}
