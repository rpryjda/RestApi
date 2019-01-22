package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.exceptions.EmptyUsersListException;
import com.pryjda.RestApi.model.request.UserRequest;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.model.validation.order.userRequest.ConstraintsOrderForUserRequestAndPostMethod;
import com.pryjda.RestApi.model.validation.order.userRequest.ConstraintsOrderForUserRequestAndPutMethod;
import com.pryjda.RestApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostFilter("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(filterObject.getEmail())) or " +
            "(authentication.getName().equals(filterObject.indexNumber.toString())))")
    public List<UserResponse> retrieveAllUsers() {
        List<UserResponse> usersResponse = userService.getUsers();

        return usersResponse.stream()
                .findAny()
                .map(item->usersResponse)
                .orElseThrow(()->new EmptyUsersListException("Empty list of users"));
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PostAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(returnObject.getEmail())) or " +
            "(authentication.getName().equals(returnObject.indexNumber.toString())))")
    public UserResponse retrieveUserById(@PathVariable(value = "id") Long id) {
        UserResponse foundUserResponse = userService.getUser(id);

        return foundUserResponse;
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> createUser(@Validated(value = ConstraintsOrderForUserRequestAndPostMethod.class)
                                                   @RequestBody UserRequest userRequest) {

        UserResponse createdUserResponse = userService.createUser(userRequest);

        return new ResponseEntity<>(createdUserResponse, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "@securityServiceImpl.isIdOfLoggedUser(#id))")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long id) {

        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/users/{id}/password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "@securityServiceImpl.isIdOfLoggedUser(#id))")
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
