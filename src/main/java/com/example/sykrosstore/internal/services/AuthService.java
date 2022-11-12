package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.configuration.security.jwt.JWTUtils;
import com.example.sykrosstore.configuration.security.reponse.JwtResponse;
import com.example.sykrosstore.configuration.security.service.UserDetail;
import com.example.sykrosstore.constants.DatabaseOperation;
import com.example.sykrosstore.constants.common.controller.advice.DatabaseOperationException;
import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.constants.common.controller.auth.AccountConstants;
import com.example.sykrosstore.custom.responseEntity.Message;
import com.example.sykrosstore.entities.Account;
import com.example.sykrosstore.entities.Customers;
import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.internal.controller.dto.auth.SignInRequest;
import com.example.sykrosstore.internal.controller.dto.auth.SignUpRequest;
import com.example.sykrosstore.internal.repositories.RoleRepository;
import com.example.sykrosstore.internal.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService implements IAuthService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;


    JWTUtils jwtUtils;

    AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       JWTUtils jwtUtils,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;

    }

    public AuthService injectPasswordEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
        return this;
    }

    @Transactional
    public Account signUp(SignUpRequest signUpRequest) throws EntityException {
        List<Role> roles = new ArrayList<>();
        roles.add(this.getRole(AccountConstants.ROLE_KEY.USER));
        roles.add(this.getRole(AccountConstants.ROLE_KEY.CUSTOMER));
        Customers customers = Customers.buildCustomerBySignUp(signUpRequest);

        Account account = Account.buildForSignup(
                        signUpRequest.getPassword(),
                        signUpRequest.getUserName()
                ).setRole(roles).buildCustomer(customers)
                .setPassWordEncoder(this.encoder);

        this.userRepository.save(account);

        return account;
    }

    private Role getRole(AccountConstants.ROLE_KEY key) {
        return this.roleRepository.findRoleById(AccountConstants.getRoleId(key)).orElseGet(() -> null);
    }

    public JwtResponse signIn(SignInRequest sign_) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(sign_.getUserName(), sign_.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwtToken(authentication);
        UserDetail userDetails = (UserDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return userDetails.buildJWTResponse(jwt);
    }

    @Transactional
    public String clearUserByUserName(String userName) throws DatabaseOperationException {
        try {
           this.userRepository.deleteAllByUserName(userName);
        }catch (Exception e){
            e.printStackTrace();
            throw new DatabaseOperationException("FAILED")
                    .setOpType(DatabaseOperation.DELETE);
        }
        return "Operation Success";
    }



}
