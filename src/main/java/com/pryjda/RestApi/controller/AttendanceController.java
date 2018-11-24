package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class AttendanceController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LectureRepository lectureRepository;

    @PostMapping("/registery/{id_lecture}/{id_student}")
    public void assignToAttendanceList(@PathVariable(value = "id_student") Long idStudent,
                                       @PathVariable(value = "id_lecture") Long idLecture) {

        Student student = studentRepository
                .findById(idStudent)
                .orElseThrow(() -> new RuntimeException("Student with id=" + idStudent + " doesn't exist"));

        Lecture lecture = lectureRepository
                .findById(idLecture)
                .orElseThrow(() -> new RuntimeException("Lecture with id=" + idLecture + " doesn't exist"));

        Set<Student> attendanceRegistery = lecture.getAttendanceList();
        attendanceRegistery.add(student);
        lectureRepository.save(lecture);

    }

}
