package com.pryjda.RestApi.repository;

import com.pryjda.RestApi.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
}
