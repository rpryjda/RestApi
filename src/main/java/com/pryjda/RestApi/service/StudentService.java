package com.pryjda.RestApi.service;

import com.pryjda.RestApi.model.request.StudentRequest;
import com.pryjda.RestApi.model.response.StudentResponse;

import java.util.List;

public interface StudentService {

    List<StudentResponse> getStudents();

    StudentResponse getStudent(Long studentId);

    StudentResponse createStudent(StudentRequest studentRequest);

    boolean updateStudent(Long studentId, StudentRequest studentRequest);

    boolean deleteStudent(Long studentId);

    StudentResponse getStudentByEmail(String email);

    boolean updateStudentByEmail(String email, StudentRequest studentRequest);

    boolean resetPassword(Long studentId, String password);

    boolean resetPasswordByEmail(String email, String password);
}
