package com.example.sykrosstore.internal.controller.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignInRequest {
    @NotNull
    @NotEmpty
    String userName;
    
    @NotNull
    @NotEmpty
    String password;
}
