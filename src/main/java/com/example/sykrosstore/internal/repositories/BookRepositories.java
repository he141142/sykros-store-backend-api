package com.example.sykrosstore.internal.repositories;

import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.entities.Role;
import org.hibernate.sql.Update;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepositories extends JpaRepository<Books, Long> {
    @Override
    <S extends Books> S save(S entity);

    @Override
    <S extends Books> List<S> saveAll(Iterable<S> entities);

    @Override
    Optional<Books> findById(Long aLong);
}
