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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;

    public AuthService(@Autowired
                       UserRepository userRepository,
                       @Autowired
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public AuthService injectPasswordEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
        return this;
    }

    ;

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

}
