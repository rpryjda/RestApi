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
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student with id=" + id + " doesn't exist"));
    }

    @PutMapping("/students/{id}")
    public Student updateStudentById(@PathVariable(value = "id") Long id, @RequestBody Student changedStudent) {

        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(changedStudent.getName());
                    student.setSurname(changedStudent.getSurname());
                    student.setEmail(changedStudent.getEmail());
                    student.setAcademicYear(changedStudent.getAcademicYear());
                    student.setCourseOfStudy(changedStudent.getCourseOfStudy());
                    student.setIndexNumber(changedStudent.getIndexNumber());
                    student.setPassword(changedStudent.getPassword());
                    return studentRepository.save(student);
                })
                .orElseGet(() -> {
                    changedStudent.setId(id);
                    return studentRepository.save(changedStudent);
                });
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable(value = "id") Long id) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id=" + id + " doesn't exist"));
        studentRepository.delete(student);

        return ResponseEntity.ok().build();
    }

}
