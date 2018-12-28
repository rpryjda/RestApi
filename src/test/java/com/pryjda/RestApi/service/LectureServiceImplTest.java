package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;
import com.pryjda.RestApi.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class LectureServiceImplTest {

    @InjectMocks
    private LectureServiceImpl lectureService;

    @Mock
    private LectureRepository lectureRepository;

    private Lecture lecture;

    private LectureRequest lectureRequest;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setEmail("example@gmail.com");
        user.setId(1L);

        lecture = new Lecture();
        lecture.setId(1L);
        lecture.setTitle("JavaDev 1");
        lecture.setDescription("Java programming");
        lecture.setLecturer("James Tyson");
        lecture.setAttendanceList(new HashSet<>(Arrays.asList(user)));

        lectureRequest = new LectureRequest();
        lectureRequest.setTitle("JavaDev 1");
        lectureRequest.setDescription("Java programming");
        lectureRequest.setLecturer("James Tyson");
    }

    @Test
    void shouldGetListOfLectureResponseObjects() {
        //given
        List<Lecture> lectures = Arrays.asList(lecture);
        when(lectureRepository.findAll()).thenReturn(lectures);

        //when
        List<LectureResponse> lecturesResponse = lectureService.getLectures();

        //then
        assertEquals(1, lecturesResponse.size());
        assertEquals("Java programming", lecturesResponse.get(0).getDescription());
        assertEquals(1, lecturesResponse.get(0).getAttendanceList().size());
    }

    @Test
    void shouldGetLectureResponseObject() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.of(lecture));

        //when
        LectureResponse lectureResponse = lectureService.getLectureById(13L);

        //then
        assertNotNull(lectureResponse);
        assertEquals("Java programming", lectureResponse.getDescription());
        assertEquals(1, lectureResponse.getAttendanceList().size());
    }

    @Test
    void shouldThrowWrongLectureIdExceptionWhenCallingWrongLectureId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        //when

        //then
        assertThrows(WrongLectureIdException.class,
                () -> lectureService.getLectureById(444L));
    }

    @Test
    void shouldCreateLectureAndReturnLectureResponseObject() {
        //given
        when(lectureRepository.save(any(Lecture.class))).thenReturn(lecture);

        //when
        LectureResponse lectureResponse = lectureService.createLecture(lectureRequest);

        //then
        assertSame(1L, lectureResponse.getId());
        assertEquals("JavaDev 1", lectureResponse.getTitle());
        assertEquals("Java programming", lectureResponse.getDescription());
        assertEquals("James Tyson", lectureResponse.getLecturer());
    }

    @Test
    void shouldUpdateLectureAndReturnTrueWhenExistsLectureWithIndicatedId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.of(lecture));

        //when
        boolean result = lectureService.updateLecture(13L, lectureRequest);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenNotExistsLectureWithIndicatedId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        //when
        boolean result = lectureService.updateLecture(444L, lectureRequest);

        //then
        assertFalse(result);
    }


    @Test
    void shouldDeleteLectureAndReturnTrueWhenExistsLectureWithIndicatedId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.of(lecture));

        //when
        boolean result = lectureService.deleteLecture(13L);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenDeleteLectureThatNotExists() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        //when
        boolean result = lectureService.deleteLecture(444L);

        //then
        assertFalse(result);
    }
}