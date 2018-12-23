package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.request.UserRequest;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.service.UserService;
import com.pryjda.RestApi.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> retrieveAllUsers() {
        List<UserResponse> usersResponse = userService.getUsers();

        return usersResponse
                .stream()
                .findFirst()
                .map(student -> new ResponseEntity<>(usersResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse createdUserResponse = userService.createUser(userRequest);

        return new ResponseEntity<>(createdUserResponse, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> retrieveUserById(@PathVariable(value = "id") Long id) {
        UserResponse foundUserResponse = userService.getUser(id);
        Optional<UserResponse> optionalUser = Optional.ofNullable(foundUserResponse);


        return optionalUser
                .map(user -> new ResponseEntity<>(foundUserResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable(value = "id") Long id, @RequestBody UserRequest userRequest) {

        boolean isUpdated = userService.updateUser(id, userRequest);
        if (isUpdated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long id) {

        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/my-data")
    public ResponseEntity<UserResponse> retrieveLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        UserResponse loggedUserResponse = userService.getUserByEmail(email);
        Optional<UserResponse> response = Optional.ofNullable(loggedUserResponse);

        return response
                .map(item -> new ResponseEntity<>(loggedUserResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/my-data")
    public ResponseEntity<?> updateLoggedUser(@RequestBody UserRequest userRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isUpdated = userService.updateUserByEmail(email, userRequest);
        if (isUpdated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/users/password/{id}")
    public ResponseEntity<?> updateUsersPasswordById(@PathVariable(value = "id") Long id, @RequestBody String newPassword) {

        boolean isChanged = userService.resetPassword(id, newPassword);
        if (isChanged) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePasswordForLoggedUser(@RequestBody String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();

        boolean isUpdatedByIndexNumber = false;
        boolean isUpdatedByEmail = userService.resetPasswordByEmail(authName, newPassword);
        if (Helpers.isNumber(authName)) {
            isUpdatedByIndexNumber = userService.resetPasswordByIndexNumber(Integer.parseInt(authName), newPassword);
        }

        if (isUpdatedByEmail || isUpdatedByIndexNumber) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
