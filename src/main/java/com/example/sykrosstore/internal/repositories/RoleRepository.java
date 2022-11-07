package com.example.sykrosstore.internal.repositories;

import com.example.sykrosstore.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    <S extends Role> S save(Role entity);

    <S extends Role> Iterable<S> saveAll(Iterable<S> entities);

    <S extends Role> Optional<S> findRoleById(Long id);

    <S extends Role> Optional<S> findRoleByName(String name);

    @Query("select r.id, r.name,r.amount,r.authority from Role r " +
            "inner join user_role ur " +
            "on ur.role.id = r.id " +
            "inner join Account ac on ur.account.id = ac.id where ur.account.id = ?1")
    List<Role> getRoleByAccountId(Long accountId);



}
