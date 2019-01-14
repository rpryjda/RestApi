package com.pryjda.RestApi.repository;

import com.pryjda.RestApi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRolesByName(String name);
}
