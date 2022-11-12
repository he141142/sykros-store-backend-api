package com.example.sykrosstore.custom.responseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListResponseMessage<T> extends Message<T> {
    public ListResponseMessage(builderMessage<T> builderMessage) {
        super(builderMessage);
    }





}
