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

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> retrieveUserById(@PathVariable(value = "id") Long id) {
        UserResponse foundUserResponse = userService.getUser(id);

        return new ResponseEntity<>(foundUserResponse, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse createdUserResponse = userService.createUser(userRequest);

        return new ResponseEntity<>(createdUserResponse, HttpStatus.CREATED);
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
        String authName = auth.getName();

        UserResponse loggedUserResponse;
        if (Helpers.isNumber(authName)) {
            loggedUserResponse = userService.getUserByIndexNumber(Integer.parseInt(authName));
        } else {
            loggedUserResponse = userService.getUserByEmail(authName);
        }
        Optional<UserResponse> response = Optional.ofNullable(loggedUserResponse);

        return response
                .map(item -> new ResponseEntity<>(loggedUserResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/my-data")
    public ResponseEntity<?> updateLoggedUser(@RequestBody UserRequest userRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();

        boolean isUpdated;
        if (Helpers.isNumber(authName)) {
            isUpdated = userService.updateUserByIndexNumber(Integer.parseInt(authName), userRequest);
        } else {
            isUpdated = userService.updateUserByEmail(authName, userRequest);
        }

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

        boolean isUpdated = false;
        if (Helpers.isNumber(authName)) {
            isUpdated = userService.resetPasswordByIndexNumber(Integer.parseInt(authName), newPassword);
        } else {
            isUpdated = userService.resetPasswordByEmail(authName, newPassword);
        }

        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
