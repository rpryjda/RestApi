package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.request.StudentRequest;
import com.pryjda.RestApi.model.response.StudentResponse;
import com.pryjda.RestApi.service.StudentService;
import com.pryjda.RestApi.shared.dto.StudentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static final ModelMapper mapper = new ModelMapper();

    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> retrieveAllStudents() {
        List<StudentDTO> studentsDTO = studentService.getStudents();
        List<StudentResponse> studentsResponse = new ArrayList<>();
        for (StudentDTO item : studentsDTO) {
            studentsResponse.add(mapper.map(item, StudentResponse.class));
        }
        return studentsResponse
                .stream()
                .findFirst()
                .map(student -> new ResponseEntity<>(studentsResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        StudentDTO studentDTO = mapper.map(studentRequest, StudentDTO.class);
        StudentDTO createdStudentDTO = studentService.createStudent(studentDTO);
        StudentResponse createdStudentResponse = mapper.map(createdStudentDTO, StudentResponse.class);
        return new ResponseEntity<>(createdStudentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponse> retrieveStudentById(@PathVariable(value = "id") Long id) {
        StudentDTO foundStudentDTO = studentService.getStudent(id);
        StudentResponse studentResponse = mapper.map(foundStudentDTO, StudentResponse.class);
        Optional<StudentResponse> optionalStudent = Optional.ofNullable(studentResponse);

        return optionalStudent
                .map(student -> new ResponseEntity<>(studentResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable(value = "id") Long id, @RequestBody StudentRequest studentRequest) {
        StudentDTO studentDTO = mapper.map(studentRequest, StudentDTO.class);

        boolean isUpdated = studentService.updateStudent(id, studentDTO);
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

        StudentDTO loggedStudentDTO = studentService.getStudentByEmail(email);
        StudentResponse loggedStudentResponse = mapper.map(loggedStudentDTO, StudentResponse.class);
        Optional<StudentResponse> response = Optional.ofNullable(loggedStudentResponse);

        return response
                .map(item -> new ResponseEntity<>(loggedStudentResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/my-data")
    public ResponseEntity<?> updateLoggedStudent(@RequestBody StudentRequest studentRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        StudentDTO studentDTO = mapper.map(studentRequest, StudentDTO.class);

        boolean isUpdated = studentService.updateStudentByEmail(email, studentDTO);
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
