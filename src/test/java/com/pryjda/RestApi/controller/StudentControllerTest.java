package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.request.StudentRequest;
import com.pryjda.RestApi.model.response.StudentResponse;
import com.pryjda.RestApi.service.StudentService;
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

class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    StudentRequest studentRequest;
    StudentResponse studentResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentRequest = new StudentRequest();
        studentRequest.setName("Robert");
        studentRequest.setSurname("Kowalski");
        studentRequest.setEmail("robert.mickiewicz@wp.pl");
        studentRequest.setPassword("123456");
        studentRequest.setIndexNumber(100300);
        studentRequest.setAcademicYear("Second");
        studentRequest.setCourseOfStudy("Civil Engineering");

        studentResponse = new StudentResponse();
        studentResponse.setName("Robert");
        studentResponse.setSurname("Kowalski");
        studentResponse.setEmail("robert.mickiewicz@wp.pl");
        studentResponse.setPassword("123456");
        studentResponse.setIndexNumber(100300);
        studentResponse.setAcademicYear("Second");
        studentResponse.setCourseOfStudy("Civil Engineering");
    }

    @Test
    void shouldRetrieveStudentByIdAndReturnHttpStatus200OK() {
        //given
        when(studentService.getStudent(anyLong())).thenReturn(studentResponse);

        //when
        ResponseEntity<StudentResponse> responseEntity = studentController.retrieveStudentById(13L);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnHttpStatus404NotFoundWhenStudentNotExistsWithIndicatedId() {
        //given
        when(studentService.getStudent(anyLong())).thenReturn(null);

        //when
        ResponseEntity<StudentResponse> responseEntity = studentController.retrieveStudentById(13L);

        //then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}