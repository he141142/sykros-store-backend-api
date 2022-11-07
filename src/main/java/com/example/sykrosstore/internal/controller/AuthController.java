package com.example.sykrosstore.internal.controller;

import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.entities.Account;
import com.example.sykrosstore.internal.controller.dto.auth.SignUpRequest;
import com.example.sykrosstore.internal.repositories.RoleRepository;
import com.example.sykrosstore.internal.repositories.UserRepository;
import com.example.sykrosstore.internal.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "auths")
public class AuthController {
    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    RoleRepository roleRepository;
    AuthService authService;
    PasswordEncoder encoder;
    public AuthController(@Autowired PasswordEncoder encoder,
                          @Autowired
                          RoleRepository roleRepository,
                          @Autowired
                          UserRepository userRepository,
                          @Autowired
                          AuthenticationManager authenticationManager,
                          @Autowired AuthService authService

                          ){
                this.roleRepository = roleRepository;
                this.userRepository = userRepository;
                this.authenticationManager = authenticationManager;
                this.encoder = encoder;
                this.authService = authService.injectPasswordEncoder(encoder);
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public ResponseEntity<?> SignUpMember(@Valid @RequestBody SignUpRequest signUpRequest) throws EntityException {
        Account account = this.authService.signUp(signUpRequest);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
