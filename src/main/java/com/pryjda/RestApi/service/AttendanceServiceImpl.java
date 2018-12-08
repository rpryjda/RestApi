package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public boolean createRecordIntoAttendanceList(Long lectureID, String studentEmail) {

        return lectureRepository.findById(lectureID)
                .map(lecture -> {
                    Student loggedStudent = studentRepository.findStudentByEmail(studentEmail);
                    Optional<Student> optionalStudent = Optional.ofNullable(loggedStudent);
                    return optionalStudent
                            .map(student -> {
                                lecture.getAttendanceList().add(loggedStudent);
                                lectureRepository.save(lecture);
                                return true;
                            })
                            .orElse(false);
                })
                .orElse(false);
    }
}
