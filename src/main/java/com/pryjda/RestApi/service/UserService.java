package com.pryjda.RestApi.service;

import com.pryjda.RestApi.model.request.UserRequest;
import com.pryjda.RestApi.model.response.UserResponse;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {

    @PostFilter("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(filterObject.getEmail())) or " +
            "(authentication.getName().equals(filterObject.indexNumber.toString())))")
    List<UserResponse> getUsers();

    @PostAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(returnObject.getEmail())) or " +
            "(authentication.getName().equals(returnObject.indexNumber.toString())))")
    UserResponse getUser(Long userId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    UserResponse createUser(UserRequest userRequest);

    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "@securityServiceImpl.isIdOfLoggedUser(#userId))")
    boolean updateUser(Long userId, UserRequest userRequest);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean deleteUser(Long userId);

    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "@securityServiceImpl.isIdOfLoggedUser(#userId))")
    boolean resetPassword(Long userId, String password);
}
