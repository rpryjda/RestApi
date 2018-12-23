package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.request.UserRequest;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userRequest = new UserRequest();
        userRequest.setName("Robert");
        userRequest.setSurname("Mickiewicz");
        userRequest.setEmail("robert.mickiewicz@wp.pl");
        userRequest.setPassword("123456");
        userRequest.setIndexNumber(100300);
        userRequest.setAcademicYear("Second");
        userRequest.setCourseOfStudy("Civil Engineering");

        userResponse = new UserResponse();
        userResponse.setName("Robert");
        userResponse.setSurname("Mickiewicz");
        userResponse.setEmail("robert.mickiewicz@wp.pl");
        userResponse.setPassword("123456");
        userResponse.setIndexNumber(100300);
        userResponse.setAcademicYear("Second");
        userResponse.setCourseOfStudy("Civil Engineering");
    }

    @Test
    void shouldRetrieveUserByIdAndReturnHttpStatus200OK() {
        //given
        when(userService.getUser(anyLong())).thenReturn(userResponse);

        //when
        ResponseEntity<UserResponse> responseEntity = userController.retrieveUserById(13L);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnHttpStatus404NotFoundWhenUserNotExistsWithIndicatedId() {
        //given
        when(userService.getUser(anyLong())).thenReturn(null);

        //when
        ResponseEntity<UserResponse> responseEntity = userController.retrieveUserById(13L);

        //then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}