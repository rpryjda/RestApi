package com.pryjda.RestApi.repository;

import com.pryjda.RestApi.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
