package com.example.sykrosstore.internal.repositories;

import com.example.sykrosstore.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUserName(String username);
    Boolean existsByUserName(String username);
}
