package com.example.sykrosstore.constants.common.controller.advice;

public class UpdateException extends Exception {
    String entity = null;

    public UpdateException(String msg) {
        super(msg);
    }

    public UpdateException setEntity(String modelName) {
        this.entity = modelName;
        return this;
    }

    @Override
    public String getMessage() {
        String baseMsg = super.getMessage();
        return this.entity != null ? baseMsg + "[model: " + this.entity + "]"
                : baseMsg;
    }
}
