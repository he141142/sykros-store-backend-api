package com.example.sykrosstore.custom.responseEntity;

import com.example.sykrosstore.constants.common.err.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponseEntityCustom<T> extends ResponseEntity<T> {
    public ResponseEntityCustom(HttpStatus status) {
        super(status);
    };

    public static ResponseEntityCustom<?> buildErrorMessage (ErrorResponse errorResponse){
            return new ResponseEntityCustom<>(errorResponse,errorResponse.getStatus());
    };

    public static ResponseEntityCustom<?> buildEntityResponseMsg (Message<?> msg ){
            return new ResponseEntityCustom<>(msg,msg.getStatus());
    }

    public ResponseEntityCustom(T body, HttpStatus status) {
        super(body, status);
    }


    public ResponseEntityCustom(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseEntityCustom(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super( body, headers, status);
    }

    public ResponseEntityCustom(T body, MultiValueMap<String, String> headers, int rawStatus) {
        super( body, headers, rawStatus);
    }
}
