package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.request.StudentRequest;
import com.pryjda.RestApi.model.response.StudentResponse;
import com.pryjda.RestApi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> retrieveAllStudents() {
        List<StudentResponse> studentsResponse = studentService.getStudents();

        return studentsResponse
                .stream()
                .findFirst()
                .map(student -> new ResponseEntity<>(studentsResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        StudentResponse createdStudentResponse = studentService.createStudent(studentRequest);

        return new ResponseEntity<>(createdStudentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponse> retrieveStudentById(@PathVariable(value = "id") Long id) {
        StudentResponse foundStudentResponse = studentService.getStudent(id);
        Optional<StudentResponse> optionalStudent = Optional.ofNullable(foundStudentResponse);

        return optionalStudent
                .map(student -> new ResponseEntity<>(foundStudentResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable(value = "id") Long id, @RequestBody StudentRequest studentRequest) {

        boolean isUpdated = studentService.updateStudent(id, studentRequest);
        if (isUpdated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable(value = "id") Long id) {

        boolean isDeleted = studentService.deleteStudent(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/my-data")
    public ResponseEntity<StudentResponse> retrieveLoggedStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        StudentResponse loggedStudentResponse = studentService.getStudentByEmail(email);
        Optional<StudentResponse> response = Optional.ofNullable(loggedStudentResponse);

        return response
                .map(item -> new ResponseEntity<>(loggedStudentResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/my-data")
    public ResponseEntity<?> updateLoggedStudent(@RequestBody StudentRequest studentRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isUpdated = studentService.updateStudentByEmail(email, studentRequest);
        if (isUpdated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/students/alter/password/{id}")
    public ResponseEntity<?> updateStudentsPasswordById(@PathVariable(value = "id") Long id, @RequestBody String newPassword) {

        boolean isChanged = studentService.resetPassword(id, newPassword);
        if (isChanged) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/alter/password")
    public ResponseEntity<?> updatePasswordForLoggedStudent(@RequestBody String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isUpdated = studentService.resetPasswordByEmail(email, newPassword);
        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
