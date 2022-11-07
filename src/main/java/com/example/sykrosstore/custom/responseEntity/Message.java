package com.example.sykrosstore.custom.responseEntity;

import com.example.sykrosstore.constants.common.err.ErrorResponse;
import org.springframework.http.HttpStatus;

public class Message<T> {

    ErrorResponse errorResponse = null;
    T Data = null;
    String message;
    HttpStatus httpStatus;

    public Message<T> buildErrorResponse(HttpStatus httpStatus,
                                         String message) {
        this.errorResponse = new ErrorResponse().build(
                httpStatus, message
        );
        return this;
    }


    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getStatusReason() {
        return this.httpStatus.getReasonPhrase();
    }

    public HttpStatus getStatus() {
        return this.httpStatus;
    }

    public ResponseEntityCustom<?> buildResponseEntity() {
        if (this.errorResponse != null) {
            return ResponseEntityCustom.buildErrorMessage(this.errorResponse);
        }
        return ResponseEntityCustom.buildEntityResponseMsg(this);
    }

}
