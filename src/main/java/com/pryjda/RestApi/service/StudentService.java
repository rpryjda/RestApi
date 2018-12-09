package com.pryjda.RestApi.service;

import com.pryjda.RestApi.shared.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getStudents();

    StudentDTO getStudent(Long studentId);

    StudentDTO createStudent(StudentDTO studentDTO);

    boolean updateStudent(Long studentId, StudentDTO studentDTO);

    boolean deleteStudent(Long studentId);

    StudentDTO getStudentByEmail(String email);

    boolean updateStudentByEmail(String email, StudentDTO studentDTO);

    boolean resetPassword(Long studentId, String password);

    boolean resetPasswordByEmail(String email, String password);
}
