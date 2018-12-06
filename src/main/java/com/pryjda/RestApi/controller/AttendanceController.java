package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AttendanceController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LectureRepository lectureRepository;

    @PostMapping("/registry/lectures/{id_lecture}")
    public ResponseEntity<?> assignLoggedStudentToAttendanceList(@PathVariable(value = "id_lecture") Long idLecture) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return lectureRepository.findById(idLecture)
                .map(lecture -> {
                    Student student = studentRepository.findStudentByEmail(email);

                    Optional<Student> optionalStudent = Optional.ofNullable(student);

                    return optionalStudent
                            .map(item -> {
                                lecture.getAttendanceList().add(student);
                                lectureRepository.save(lecture);
                                return new ResponseEntity<>(HttpStatus.CREATED);
                            })
                            .orElse(ResponseEntity.notFound().build());
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
