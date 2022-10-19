package com.example.sykrosstore.common.controller.advice;

import com.example.sykrosstore.common.err.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

public class CustomExcp implements IGlobalExcp {

    ErrorDetail errorDetail;
    HttpStatus status = null;

    public CustomExcp(Builder builder) {
        this.status = builder.status;
        this.errorDetail = builder.errorDetail;
    }

    @Override
    public ResponseEntity<?> getResponseExp(Exception ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail
                .Builder()
                .setTime(new Date())
                .build(ex.getMessage(), request.getDescription(false));
        if (this.status == null) {
            return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(errorDetail, this.status);
    }

    @Override
    public ResponseEntity<?> getResponseExp() {
        return null;
    }

    public static class Builder {
        ErrorDetail errorDetail = null;
        HttpStatus status = null;

        public Builder customError(ErrorDetail errorDetail, HttpStatus status) {
            this.errorDetail = errorDetail;
            this.status = status;
            return this;
        }

        public Builder setStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder setErr(ErrorDetail errorDetail) {
            this.errorDetail = errorDetail;
            return this;
        }

        public CustomExcp build() {
            return new CustomExcp(this);
        }

    }
}