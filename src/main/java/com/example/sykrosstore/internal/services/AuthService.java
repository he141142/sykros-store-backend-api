package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.constants.common.controller.auth.AccountConstants;
import com.example.sykrosstore.entities.Account;
import com.example.sykrosstore.entities.Customers;
import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.internal.controller.dto.auth.SignUpRequest;
import com.example.sykrosstore.internal.repositories.RoleRepository;
import com.example.sykrosstore.internal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public Account signUp(SignUpRequest signUpRequest) throws EntityException {
        List<Role> roles = new ArrayList<>();
        roles.add(this.getRole(AccountConstants.ROLE_KEY.USER));
        roles.add(this.getRole(AccountConstants.ROLE_KEY.CUSTOMER));
        Customers customers = Customers.buildCustomerBySignUp(signUpRequest);

        Account account = Account.buildForSignup(
                signUpRequest.getPassword(),
                signUpRequest.getUserName()
        ).setRole(roles).buildCustomer(customers);

        try {
            this.userRepository.save(account);
        }catch (Exception e){
            throw new EntityException("Cant create Account");
        }
        return account;
    }

    private Role getRole(AccountConstants.ROLE_KEY key){
        return this.roleRepository.findRoleById(AccountConstants.getRoleId(key)).orElseGet(() -> null);
    }

}
