package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.exceptions.StudentServiceException;
import com.pryjda.RestApi.repository.StudentRepository;
import com.pryjda.RestApi.shared.dto.StudentDTO;
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
    public List<StudentDTO> getStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentsDTO = new ArrayList<>();
        for (Student item : students) {
            studentsDTO.add(mapper.map(item, StudentDTO.class));
        }
        return studentsDTO;
    }

    @Override
    public StudentDTO getStudent(Long studentId) {
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(()->new StudentServiceException("wrong student id"));
        StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
        return studentDTO;
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = mapper.map(studentDTO, Student.class);
        Student createdStudent = studentRepository.save(student);
        StudentDTO createdStudentDTO = mapper.map(createdStudent, StudentDTO.class);
        return createdStudentDTO;
    }

    @Override
    public boolean updateStudent(Long studentId, StudentDTO changedStudentDTO) {
        Student changedStudent = mapper.map(changedStudentDTO, Student.class);

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
    public StudentDTO getStudentByEmail(String email) {
        Student student = studentRepository.findStudentByEmail(email);
        StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
        return studentDTO;
    }

    @Override
    public boolean updateStudentByEmail(String email, StudentDTO changedStudentDTO) {
        Student studentBeforeChanges = studentRepository.findStudentByEmail(email);
        Student changedStudent = mapper.map(changedStudentDTO, Student.class);
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
