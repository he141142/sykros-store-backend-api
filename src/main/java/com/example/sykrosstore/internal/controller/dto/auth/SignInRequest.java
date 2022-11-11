package com.example.sykrosstore.internal.controller.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignInRequest {
    @NotNull
    @NotEmpty
    @JsonProperty("user_name")
    String userName;
    
    @NotNull
    @NotEmpty
    @JsonProperty("password")
    String password;
}
