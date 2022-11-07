package com.example.sykrosstore.constants.common.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface IGlobalExcp {

     ResponseEntity<?> getResponseExp(Exception ex, WebRequest request);
     ResponseEntity<?> getResponseExp();

}