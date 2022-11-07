package com.example.sykrosstore.custom.validation;

public class Violation {
    private final String fieldName;
    private final String msg;

    public Violation(String fieldName, String msg) {
        this.fieldName = fieldName;
        this.msg = msg;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMsg() {
        return msg;
    }
}
