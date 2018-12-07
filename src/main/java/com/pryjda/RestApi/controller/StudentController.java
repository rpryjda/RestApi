package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.entities.Student;
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
    public ResponseEntity<List<Student>> retrieveAllStudents() {

        return studentService.getStudents()
                .stream()
                .findFirst()
                .map(student -> new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> retrieveStudentById(@PathVariable(value = "id") Long id) {
        Student foundStudent = studentService.getStudent(id);
        Optional<Student> optionalStudent = Optional.ofNullable(foundStudent);

        return optionalStudent
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable(value = "id") Long id, @RequestBody Student changedStudent) {
        boolean isUpdated = studentService.updateStudent(id, changedStudent);
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
    public ResponseEntity<Student> retrieveLoggedStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student loggedStudent = studentService.getStudentByEmail(email);
        Optional<Student> response = Optional.ofNullable(loggedStudent);

        return response
                .map(item -> new ResponseEntity<>(loggedStudent, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/my-data")
    public ResponseEntity<?> updateLoggedStudent(@RequestBody Student changedStudent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isUpdated = studentService.updateStudentByEmail(email, changedStudent);

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
        if(isUpdated){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
