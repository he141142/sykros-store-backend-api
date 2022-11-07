package com.example.sykrosstore.internal.repositories;

import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositories extends JpaRepository<Customers, Long> {
    @Override
    <S extends Customers> S save(S entity);
}
