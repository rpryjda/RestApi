package com.pryjda.RestApi.repository;

import com.pryjda.RestApi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
