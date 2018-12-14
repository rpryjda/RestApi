package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.exceptions.StudentServiceException;
import com.pryjda.RestApi.model.request.StudentRequest;
import com.pryjda.RestApi.model.response.StudentResponse;
import com.pryjda.RestApi.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public List<StudentResponse> getStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentsResponse = new ArrayList<>();
        for (Student item : students) {
            studentsResponse.add(mapper.map(item, StudentResponse.class));
        }
        return studentsResponse;
    }

    @Override
    public StudentResponse getStudent(Long studentId) {
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new StudentServiceException("wrong student id"));
        StudentResponse studentResponse = mapper.map(student, StudentResponse.class);
        return studentResponse;
    }

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        Student student = mapper.map(studentRequest, Student.class);
        Student createdStudent = studentRepository.save(student);
        StudentResponse createdStudentResponse = mapper.map(createdStudent, StudentResponse.class);
        return createdStudentResponse;
    }

    @Override
    public boolean updateStudent(Long studentId, StudentRequest changedStudentRequest) {
        Student changedStudent = mapper.map(changedStudentRequest, Student.class);

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
    public StudentResponse getStudentByEmail(String email) {
        Student student = studentRepository.findStudentByEmail(email);
        StudentResponse studentResponse = mapper.map(student, StudentResponse.class);
        return studentResponse;
    }

    @Override
    public boolean updateStudentByEmail(String email, StudentRequest changedStudentRequest) {
        Student studentBeforeChanges = studentRepository.findStudentByEmail(email);
        Student changedStudent = mapper.map(changedStudentRequest, Student.class);
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
