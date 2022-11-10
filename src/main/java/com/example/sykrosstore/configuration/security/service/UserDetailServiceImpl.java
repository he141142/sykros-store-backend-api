package com.example.sykrosstore.configuration.security.service;

import com.example.sykrosstore.entities.Account;
import com.example.sykrosstore.internal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    public UserDetailServiceImpl(@Autowired UserRepository _userRepository) {
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = this.userRepository.findAccountByUserName(username);
        return account.map(UserDetail::buildUser).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }
}
