package com.example.sykrosstore.internal.repositories;

import com.example.sykrosstore.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUserName(String username);
    Optional<Account> findFirstByUserName(String username);
    Boolean existsByUserName(String username);

    void deleteAllByUserName(@NotNull @NotEmpty String userName);
}
