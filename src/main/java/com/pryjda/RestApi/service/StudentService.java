package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    Student getStudent(Long studentId);

    Student createStudent(Student student);

    boolean updateStudent(Long studentId, Student student);

    boolean deleteStudent(Long studentId);

    Student getStudentByEmail(String email);

    boolean updateStudentByEmail(String email, Student student);

    boolean resetPassword(Long studentId, String password);

    boolean resetPasswordByEmail(String email, String password);
}
