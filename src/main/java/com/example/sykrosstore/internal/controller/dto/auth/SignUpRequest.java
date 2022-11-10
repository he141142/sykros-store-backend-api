package com.example.sykrosstore.internal.controller.dto.auth;

import com.example.sykrosstore.constants.EntityValidators.CustomerValidator;
import com.example.sykrosstore.internal.decoreators.interfaces.UniqueAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SignUpRequest {
    @NotNull
    @NotEmpty
    @UniqueAccount
    @JsonProperty("user_name")
    String userName;

    @JsonProperty("password")
    @NotNull()
    @NotEmpty
    @Pattern(regexp = "((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
    String password;

    @JsonProperty("first_name")
    @NotNull(message = CustomerValidator.FIRST_NAME_MANDATORY)
    @NotEmpty(message = CustomerValidator.FIRST_NAME_MANDATORY)
    String firstName;

    @JsonProperty("last_name")
    @NotNull(message = CustomerValidator.LAST_NAME_MANDATORY)
    @NotEmpty(message = CustomerValidator.LAST_NAME_MANDATORY)
    String lastName;

    @JsonProperty("email")
    @javax.validation.constraints.NotNull(message = CustomerValidator.EMAIL_MANDATORY)
    @NotEmpty(message = CustomerValidator.EMAIL_MANDATORY)
    @Pattern(
            regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$",
            message = CustomerValidator.EMAIL_INCORRECT_FORMAT)
    @Column(name = "email", nullable = false)
    String email;

    @NotNull
    @NotEmpty
    @JsonProperty("phone_number")
    @Column(name = "phone_number")
    String phoneNumber;

    @JsonProperty("city")
    @NotNull @NotEmpty @Column String city;

    @JsonProperty("country")
    @NotNull @NotEmpty @Column String country;

    @JsonProperty("state")
    @NotNull @NotEmpty String state;
}
