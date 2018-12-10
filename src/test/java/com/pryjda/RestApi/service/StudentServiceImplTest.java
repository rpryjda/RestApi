package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Student;
import com.pryjda.RestApi.exceptions.StudentServiceException;
import com.pryjda.RestApi.repository.StudentRepository;
import com.pryjda.RestApi.shared.dto.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    @InjectMocks
    StudentServiceImpl studentService;

    @Mock
    StudentRepository studentRepository;

    Student student;

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
    }

    @Test
    void shouldGetStudent() {
        //given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //when
        StudentDTO studentDTO = studentService.getStudent(444L);

        //then
        assertNotNull(studentDTO);
        assertEquals("Robert", studentDTO.getName());
        assertEquals("Kowalski", studentDTO.getSurname());
        assertEquals("robert.mickiewicz@wp.pl", studentDTO.getEmail());
        assertEquals("123456", studentDTO.getPassword());
        assertEquals(100300, studentDTO.getIndexNumber());
        assertEquals("Second", studentDTO.getAcademicYear());
        assertEquals("Civil Engineering", studentDTO.getCourseOfStudy());
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
}