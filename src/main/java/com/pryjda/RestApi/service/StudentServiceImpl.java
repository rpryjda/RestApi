package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElse(null);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public boolean updateStudent(Long studentId, Student changedStudent) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setName(changedStudent.getName());
                    student.setSurname(changedStudent.getSurname());
                    student.setEmail(changedStudent.getEmail());
                    student.setAcademicYear(changedStudent.getAcademicYear());
                    student.setCourseOfStudy(changedStudent.getCourseOfStudy());
                    student.setIndexNumber(changedStudent.getIndexNumber());
                    student.setPassword(changedStudent.getPassword());
                    studentRepository.save(student);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteStudent(Long studentId) {

        return studentRepository.findById(studentId)
                .map(student -> {
                    studentRepository.delete(student);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findStudentByEmail(email);
    }

    @Override
    public boolean updateStudentByEmail(String email, Student changedStudent) {

        Student studentBeforeChanges = studentRepository.findStudentByEmail(email);
        Optional<Student> optionalStudent = Optional.ofNullable(studentBeforeChanges);

        return optionalStudent
                .map(student -> {
                    student.setName(changedStudent.getName());
                    student.setSurname(changedStudent.getSurname());
                    student.setCourseOfStudy(changedStudent.getCourseOfStudy());
                    student.setAcademicYear(changedStudent.getAcademicYear());
                    student.setIndexNumber(changedStudent.getIndexNumber());
                    student.setEmail(changedStudent.getEmail());
                    studentRepository.save(student);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean resetPassword(Long studentId, String password) {

        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setPassword(password);
                    studentRepository.save(student);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean resetPasswordByEmail(String email, String password) {

        Student studentByEmail = studentRepository.findStudentByEmail(email);
        Optional<Student> optionalStudent = Optional.ofNullable(studentByEmail);

        return optionalStudent
                .map(student -> {
                    student.setPassword(password);
                    studentRepository.save(student);
                    return true;
                })
                .orElse(false);
    }
}
