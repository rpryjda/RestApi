package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.repository.StudentRepository;
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
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> retrieveAllStudents() {

        return studentRepository.findAll()
                .stream()
                .findFirst()
                .map(student -> new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> retrieveStudentById(@PathVariable(value = "id") Long id) {

        return studentRepository.findById(id)
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable(value = "id") Long id, @RequestBody Student changedStudent) {

        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(changedStudent.getName());
                    student.setSurname(changedStudent.getSurname());
                    student.setEmail(changedStudent.getEmail());
                    student.setAcademicYear(changedStudent.getAcademicYear());
                    student.setCourseOfStudy(changedStudent.getCourseOfStudy());
                    student.setIndexNumber(changedStudent.getIndexNumber());
                    student.setPassword(changedStudent.getPassword());
                    studentRepository.save(student);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable(value = "id") Long id) {

        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return new ResponseEntity<>(HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my-data")
    public ResponseEntity<Student> retrieveLoggedStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student loggedStudent = studentRepository.findStudentByEmail(email);
        Optional<Student> response = Optional.ofNullable(loggedStudent);

        return response
                .map(item -> new ResponseEntity<>(loggedStudent, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/my-data")
    public ResponseEntity<?> updateLoggedStudent(@RequestBody Student changedStudent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student loggedStudent = studentRepository.findStudentByEmail(email);
        Optional<Student> response = Optional.ofNullable(loggedStudent);

        return response
                .map(student -> {
                    student.setName(changedStudent.getName());
                    student.setSurname(changedStudent.getSurname());
                    student.setCourseOfStudy(changedStudent.getCourseOfStudy());
                    student.setAcademicYear(changedStudent.getAcademicYear());
                    student.setIndexNumber(changedStudent.getIndexNumber());
                    student.setEmail(changedStudent.getEmail());
                    studentRepository.save(student);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/alter/password/{id}")
    public ResponseEntity<?> updateStudentsPasswordById(@PathVariable(value = "id") Long id, @RequestBody String newPassword) {

        return studentRepository.findById(id)
                .map(student -> {
                    student.setPassword(newPassword);
                    studentRepository.save(student);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/alter/password")
    public ResponseEntity<?> updatePasswordForLoggedStudent(@RequestBody String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student loggedStudent = studentRepository.findStudentByEmail(email);
        Optional<Student> response = Optional.ofNullable(loggedStudent);

        return response
                .map(student -> {
                    loggedStudent.setPassword(newPassword);
                    studentRepository.save(loggedStudent);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
