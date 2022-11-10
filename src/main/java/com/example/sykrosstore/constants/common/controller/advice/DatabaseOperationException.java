package com.example.sykrosstore.constants.common.controller.advice;

public class DatabaseOperationException extends Exception {
    String Operation_Type = null;

    public DatabaseOperationException(String msg) {
        super(msg);
    }

    public DatabaseOperationException setOpType(String __t) {
        this.Operation_Type = __t;
        return this;
    }

    @Override
    public String getMessage() {
        String baseMsg = super.getMessage();
        return this.Operation_Type != null ? baseMsg + "[Operation: " + this.Operation_Type + "]"
                : baseMsg;
    }
}
