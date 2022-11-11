package com.example.sykrosstore.custom.responseEntity;

import com.example.sykrosstore.constants.common.err.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Message<T> {

    ErrorResponse errorResponse = null;
    @JsonProperty("data")
    T Data = null;
    @JsonProperty("message")
    String message;
    @JsonProperty("http_sattus")
    HttpStatus httpStatus;

    public Message<T> buildErrorResponse(HttpStatus httpStatus,
                                         String message) {
        this.errorResponse = new ErrorResponse().build(
                httpStatus, message
        );
        return this;
    }


    public Message(builderMessage<T> builderMessage){
            this.message = builderMessage.message;
            this.httpStatus = builderMessage.httpStatus;
            this.Data = builderMessage.Data;
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

    public ResponseEntity<?> buildResponse(){
        return new ResponseEntity<>(
            this,this.httpStatus
        );
    }

    public static class builderMessage<T>{
        ErrorResponse errorResponse = null;
        T Data = null;
        String message;
        HttpStatus httpStatus;

        public builderMessage<?> setData(T data){
            this.Data = data;
            return this;
        }

        public builderMessage<T> setMsg(String __m){
            this.message = __m;
            return this;
        }

        public builderMessage<T> httpStatus(HttpStatus __status){
            this.httpStatus = __status;
            return this;
        }

        public Message<?> buildMsg(){
            return new Message<>(this);
        }

        public Message<T> buildMsgData(){
            return new Message<>(this);
        }

    }

}
