package com.example.sykrosstore.constants.common.err;

import java.util.Date;

public class ErrorDetail {
    private Date timeStamp;
    private String message;
    private String details;

    public ErrorDetail(String message, String details) {
        this.timeStamp = new Date();
        this.details = details;
        this.message = message;
    }

    public ErrorDetail(Builder b) {
        this.timeStamp = b.timeStamp == null ? new Date() : b.timeStamp;
        this.message = b.message;
        this.details = b.details;
    }

    public static class Builder {
        private Date timeStamp = null;
        private String message;
        private String details;

        public Builder setTime(Date date) {
            this.timeStamp = date;
            return this;
        }

        public ErrorDetail build(String message, String details) {
            this.details = details;
            this.message = message;
            return new ErrorDetail(this);
        }
    }
}