package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.constants.common.controller.auth.AccountConstants;
import com.example.sykrosstore.entities.Account;
import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.internal.controller.dto.auth.SignUpRequest;
import com.example.sykrosstore.internal.repositories.RoleRepository;
import com.example.sykrosstore.internal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements IAuthService {
    UserRepository userRepository;
    RoleRepository roleRepository;


    public AuthService(@Autowired
                       UserRepository userRepository,
                       @Autowired
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Account signUp(SignUpRequest signUpRequest){

        Account account = new Account();
        account.setPassword(signUpRequest.getPassword());
        account.setStatus(AccountConstants.ACCOUNT_STATUS_ACTIVE);

        return account;
    }

    private Role getRole(AccountConstants.ROLE_KEY key){
        return this.roleRepository.findRoleById(AccountConstants.getRoleId(key)).orElseGet(() -> null);
    }

}
