package com.example.sykrosstore.constants.common.err;

import com.example.sykrosstore.custom.validation.ValidationConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;


public class ErrorResponse {

    public enum ErrorType {
        VALIDATION_CONSTRAINT,
        DEFAULT
    }

    ErrorType type;

    ValidationConstraint validationConstraint = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timeStamp;

    private int code;

    private HttpStatus status;

    private String message;

    @JsonIgnore
    private String stackTrace;

    private Object data;

    public ErrorResponse() {
        timeStamp = new Date();
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public Object getData() {
        return data;
    }

    public ErrorResponse(
            HttpStatus httpStatus,
            String message
    ) {
        this();

        this.code = httpStatus.value();
        this.status =httpStatus;
        this.message = message;
    }

    public ErrorResponse build(HttpStatus httpStatus,
                               String message) {
        this.timeStamp = new Date();
        this.code = httpStatus.value();
        this.status = httpStatus;
        this.message = message;
        this.type = ErrorType.DEFAULT;
        return this;
    }

//    public ErrorResponse buildValidationConstraintMsg(Map<String,String> errors){
//            this.type = ErrorType.VALIDATION_CONSTRAINT;
//    }

    public ErrorResponse setStackTrace(String _stackTrace
    ) {
        this.stackTrace = _stackTrace;
        return this;
    }

    public ErrorResponse setData(Object _data
    ) {
        this.data = _data;
        return this;
    }


}
