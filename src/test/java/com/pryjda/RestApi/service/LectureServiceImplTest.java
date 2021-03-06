package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.utils.AttendanceListForLectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
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

    @Mock
    private AttendanceListForLectureService attendanceList;

    private Lecture lecture;

    private Lecture lectureThatTookPlace;

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
        lecture.setDate(LocalDateTime.of(2020, 12, 17, 17, 30, 0));
        lecture.setAttendanceList(new HashSet<>(Arrays.asList(user)));

        lectureThatTookPlace = new Lecture();
        lectureThatTookPlace.setId(2L);
        lectureThatTookPlace.setTitle("JavaDev 2");
        lectureThatTookPlace.setDescription("Java programming");
        lectureThatTookPlace.setLecturer("James Tyson");
        lectureThatTookPlace.setDate(LocalDateTime.of(2018, 12, 17, 17, 30, 0));
        lectureThatTookPlace.setAttendanceList(new HashSet<>(Arrays.asList(user)));

        lectureRequest = new LectureRequest();
        lectureRequest.setTitle("JavaDev 1");
        lectureRequest.setDescription("Java programming");
        lectureRequest.setLecturer("James Tyson");
        lectureRequest.setDate(LocalDateTime.of(2018, 12, 17, 17, 30, 0));
    }

    @Test
    void shouldGetListOfLectureResponseObjects() {
        //given
        List<Lecture> lectures = Arrays.asList(lecture);
        when(lectureRepository.findAll()).thenReturn(lectures);
        when(attendanceList.getAttendanceListFromLectureObject(any(Lecture.class))).thenReturn(new HashSet<>());

        //when
        List<LectureResponse> lecturesResponse = lectureService.getLectures();

        //then
        assertEquals(1, lecturesResponse.size());
        assertEquals("Java programming", lecturesResponse.get(0).getDescription());
        assertNotNull(lecturesResponse.get(0).getAttendanceList());
        assertEquals(0, lecturesResponse.get(0).getAttendanceList().size());
    }

    @Test
    void shouldGetLectureResponseObject() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.of(lecture));
        when(attendanceList.getAttendanceListFromLectureObject(any(Lecture.class))).thenReturn(new HashSet<>());

        //when
        LectureResponse lectureResponse = lectureService.getLectureById(13L);

        //then
        assertNotNull(lectureResponse);
        assertEquals(Long.valueOf(1L), lectureResponse.getId());
        assertEquals("Java programming", lectureResponse.getDescription());
        assertEquals(LocalDateTime.of(2020, 12, 17, 17, 30, 0), lectureResponse.getDate());
        assertNotNull(lectureResponse.getAttendanceList());
        assertEquals(0, lectureResponse.getAttendanceList().size());
    }

    @Test
    void shouldThrowWrongLectureIdExceptionWhenCallingWrongLectureId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.empty());

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
    void shouldNotUpdateLectureAndReturnFalseWhenNotExistsLectureWithIndicatedId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.empty());

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
    void shouldNotDeleteLectureAndReturnFalseWhenDeleteLectureThatTookPlace() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.of(lectureThatTookPlace));

        //when
        boolean result = lectureService.deleteLecture(444L);

        //then

        assertFalse(result);
    }

    @Test
    void shouldNotDeleteLectureAndThrowWrongLectureIdExceptionWhenCallingWrongLectureId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when

        //then
        assertThrows(WrongLectureIdException.class,
                () -> lectureService.getLectureById(444L));
    }
}