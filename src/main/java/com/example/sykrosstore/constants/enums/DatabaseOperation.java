package com.example.sykrosstore.constants.enums;

import lombok.Getter;

public enum DatabaseOperation {
    DELETE("DELETE"),
    CREATE("CREATE");

    @Getter
    private final String name;
    DatabaseOperation(String name) {
        this.name = name;
    }
}
