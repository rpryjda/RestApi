package com.pryjda.RestApi.service;

import com.pryjda.RestApi.model.request.UserRequest;
import com.pryjda.RestApi.model.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getUsers();

    UserResponse getUser(Long userId);

    UserResponse getUserByEmail(String email);

    UserResponse getUserByIndexNumber(int indexNumber);

    UserResponse createUser(UserRequest userRequest);

    boolean updateUser(Long userId, UserRequest userRequest);

    boolean updateUserByEmail(String email, UserRequest userRequest);

    boolean updateUserByIndexNumber(int indexNumber, UserRequest userRequest);

    boolean deleteUser(Long userId);

    boolean resetPassword(Long userId, String password);

    boolean resetPasswordByEmail(String email, String password);

    boolean resetPasswordByIndexNumber(int indexNumber, String password);
}
