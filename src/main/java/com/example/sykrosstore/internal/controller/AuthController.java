package com.example.sykrosstore.internal.controller;

import com.example.sykrosstore.configuration.security.reponse.JwtResponse;
import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.custom.responseEntity.Message;
import com.example.sykrosstore.entities.Account;
import com.example.sykrosstore.internal.controller.dto.auth.SignInRequest;
import com.example.sykrosstore.internal.controller.dto.auth.SignUpRequest;
import com.example.sykrosstore.internal.repositories.RoleRepository;
import com.example.sykrosstore.internal.repositories.UserRepository;
import com.example.sykrosstore.internal.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "auths")
public class AuthController {

    UserRepository userRepository;

    RoleRepository roleRepository;
    AuthService authService;
    PasswordEncoder encoder;
    public AuthController(@Autowired PasswordEncoder encoder,
                          @Autowired
                          RoleRepository roleRepository,
                          @Autowired
                          UserRepository userRepository,
                          @Autowired AuthService authService

                          ){
                this.roleRepository = roleRepository;
                this.userRepository = userRepository;
                this.encoder = encoder;
                this.authService = authService.injectPasswordEncoder(encoder);
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public ResponseEntity<?> SignUpMember(@Valid @RequestBody SignUpRequest signUpRequest) throws EntityException {
        Account account = this.authService.signUp(signUpRequest);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @RequestMapping(value = "/signIn",method = RequestMethod.POST)
    public ResponseEntity<?> signInAccount(@Valid @RequestBody SignInRequest dto){
        JwtResponse jwtResponse = this.authService.signIn(dto);
        return new Message.builderMessage<JwtResponse>()
                .setMsg("Sign in Successfully")
                .setData(jwtResponse)
                .httpStatus(HttpStatus.OK)
                .buildMsg()
                .buildResponse();
    }
}
