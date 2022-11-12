package com.example.sykrosstore.internal.repositories;

import com.example.sykrosstore.entities.Subgenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubGenresRepository extends JpaRepository<Subgenres,Long> {
}
