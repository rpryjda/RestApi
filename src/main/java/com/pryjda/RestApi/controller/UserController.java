package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.request.UserRequest;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.model.validation.order.userRequest.ConstraintsOrderForUserRequestAndPostMethod;
import com.pryjda.RestApi.model.validation.order.userRequest.ConstraintsOrderForUserRequestAndPutMethod;
import com.pryjda.RestApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    public ResponseEntity<UserResponse> createUser(@Validated(value = ConstraintsOrderForUserRequestAndPostMethod.class)
                                                   @RequestBody UserRequest userRequest) {

        UserResponse createdUserResponse = userService.createUser(userRequest);

        return new ResponseEntity<>(createdUserResponse, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable(value = "id") Long id,
                                            @Validated(value = ConstraintsOrderForUserRequestAndPutMethod.class)
                                            @RequestBody UserRequest userRequest) {

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

    @PatchMapping("/users/{id}/password")
    public ResponseEntity<?> updateUsersPasswordById(@PathVariable(value = "id") Long id,
                                                     @NotNull(message = "Password is required")
                                                     @Size(min = 3, max = 16, message = "Password should be between 3 and 16 characters")
                                                     @RequestBody String newPassword) {

        boolean isChanged = userService.resetPassword(id, newPassword);
        if (isChanged) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
