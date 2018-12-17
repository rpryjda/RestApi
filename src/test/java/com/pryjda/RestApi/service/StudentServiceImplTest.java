package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.exceptions.StudentServiceException;
import com.pryjda.RestApi.model.request.StudentRequest;
import com.pryjda.RestApi.model.response.StudentResponse;
import com.pryjda.RestApi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    @InjectMocks
    StudentServiceImpl studentService;

    @Mock
    StudentRepository studentRepository;

    Student student;
    StudentRequest studentRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        student = new Student();
        student.setName("Robert");
        student.setSurname("Kowalski");
        student.setEmail("robert.mickiewicz@wp.pl");
        student.setPassword("123456");
        student.setIndexNumber(100300);
        student.setAcademicYear("Second");
        student.setCourseOfStudy("Civil Engineering");

        studentRequest = new StudentRequest();
        studentRequest.setName("Robert");
        studentRequest.setSurname("Kowalski");
        studentRequest.setEmail("robert.mickiewicz@wp.pl");
        studentRequest.setPassword("123456");
        studentRequest.setIndexNumber(100300);
        studentRequest.setAcademicYear("Second");
        studentRequest.setCourseOfStudy("Civil Engineering");
    }

    @Test
    void shouldGetListOfStudents() {
        //given
        List<Student> students = Arrays.asList(student);
        when(studentRepository.findAll()).thenReturn(students);

        //when
        List<StudentResponse> responseStudents = studentService.getStudents();

        //then
        assertEquals(1, responseStudents.size());
    }


    @Test
    void shouldGetStudent() {
        //given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //when
        StudentResponse studentResponse = studentService.getStudent(444L);

        //then
        assertNotNull(studentResponse);
        assertEquals("Robert", studentResponse.getName());
        assertEquals("Kowalski", studentResponse.getSurname());
        assertEquals("robert.mickiewicz@wp.pl", studentResponse.getEmail());
        assertEquals("123456", studentResponse.getPassword());
        assertEquals(100300, studentResponse.getIndexNumber());
        assertEquals("Second", studentResponse.getAcademicYear());
        assertEquals("Civil Engineering", studentResponse.getCourseOfStudy());
    }

    @Test
    void shouldThrowStudentServiceExceptionWhenCallingWrongId() {
        //given
        when(studentRepository.findById(13L)).thenReturn(Optional.ofNullable(null));

        //when

        //then
        assertThrows(StudentServiceException.class, () ->
                studentService.getStudent(13L)
        );
    }


    @Test
    void shouldDeleteStudentAndReturnTrue() {
        //given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //when
        boolean result = studentService.deleteStudent(13L);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenDeleteStudentWhoNotExists() {
        //given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        //when
        boolean result = studentService.deleteStudent(13L);

        //then
        assertFalse(result);
    }

    @Test
    void shouldCreateStudentAndReturnStudentResponseObject() {
        //given
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        //when
        StudentResponse createdStudent = studentService.createStudent(studentRequest);

        //then
        assertEquals("Robert", createdStudent.getName());
        assertEquals("Kowalski", createdStudent.getSurname());
    }

    @Test
    void shouldUpdateStudentAndReturnTrueIfExistsStudentWithIndicatedId() {
        //given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //when
        boolean result = studentService.updateStudent(13L, studentRequest);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseIfNotExistsStudentWithIndicatedId() {
        //given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        //when
        boolean result = studentService.updateStudent(13L, studentRequest);

        //then
        assertFalse(result);
    }

    @Test
    void shouldGetStudentByEmail() {
        //given
        when(studentRepository.findStudentByEmail(anyString())).thenReturn(student);

        //when
        StudentResponse studentResponse = studentService.getStudentByEmail("robert.mickiewicz@wp.pl");

        //then
        assertEquals("Robert", studentResponse.getName());
    }
}