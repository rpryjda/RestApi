package com.pryjda.RestApi.repository;

import com.pryjda.RestApi.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
}
