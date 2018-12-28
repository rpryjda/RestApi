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
            "(authentication.getName().equals(filterObject.getIndexNumber())))")
    List<UserResponse> getUsers();

    @PostAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(returnObject.getEmail())) or " +
            "(authentication.getName().equals(returnObject.getIndexNumber())))")
    UserResponse getUser(Long userId);

    @PreAuthorize("hasRole('ROLE_USER')")
    UserResponse getUserByEmail(String email);

    @PreAuthorize("hasRole('ROLE_USER')")
    UserResponse getUserByIndexNumber(int indexNumber);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    UserResponse createUser(UserRequest userRequest);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean updateUser(Long userId, UserRequest userRequest);

    @PreAuthorize("hasRole('ROLE_USER')")
    boolean updateUserByEmail(String email, UserRequest userRequest);

    @PreAuthorize("hasRole('ROLE_USER')")
    boolean updateUserByIndexNumber(int indexNumber, UserRequest userRequest);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean deleteUser(Long userId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean resetPassword(Long userId, String password);

    @PreAuthorize("hasRole('ROLE_USER')")
    boolean resetPasswordByEmail(String email, String password);

    @PreAuthorize("hasRole('ROLE_USER')")
    boolean resetPasswordByIndexNumber(int indexNumber, String password);
}
