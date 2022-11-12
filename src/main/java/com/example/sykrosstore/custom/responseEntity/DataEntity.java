package com.example.sykrosstore.custom.responseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataEntity<T> {
    T data;
    int totalItem;
}
