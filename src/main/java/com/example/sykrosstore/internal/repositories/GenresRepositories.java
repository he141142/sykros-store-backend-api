package com.example.sykrosstore.internal.repositories;

import com.example.sykrosstore.entities.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresRepositories  extends JpaRepository<Genres, Long> {
    @Override
    <S extends Genres> List<S> saveAll(Iterable<S> entities);
}
