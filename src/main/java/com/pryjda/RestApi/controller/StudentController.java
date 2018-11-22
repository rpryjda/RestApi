package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> retrieveAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/students/{id}")
    public Student retrieveStudentById(@PathVariable(value = "id") Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("student with " + id + " not exist"));
    }

    @PutMapping("/students/{id}")
    public Student updateStudentById(@PathVariable(value = "id") Long id, @RequestBody Student changedStudent) {
        studentRepository.findById(id).orElseThrow(() -> new RuntimeException("student with " + id + " not exist"));
        return studentRepository.save(changedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable(value = "id") Long id){
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("student with " + id + " not exist"));
        studentRepository.delete(student);

        return ResponseEntity.ok().build();
    }

}
