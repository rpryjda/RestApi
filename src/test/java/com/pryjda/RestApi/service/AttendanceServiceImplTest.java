package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.entities.UserProfile;
import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.exceptions.WrongUserIdException;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class AttendanceServiceImplTest {

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LectureRepository lectureRepository;

    private User user;

    private UserProfile userProfile;

    private Lecture lecture;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userProfile = new UserProfile();
        userProfile.setName("Robert");
        userProfile.setSurname("Mickiewicz");
        userProfile.setAcademicYear("Second");
        userProfile.setCourseOfStudy("Civil Engineering");

        user = new User();
        user.setId(1L);
        user.setEmail("robert.mickiewicz@wp.pl");
        user.setPassword("123456");
        user.setIndexNumber(100300);
        user.setUserProfile(userProfile);

        lecture = new Lecture();
        lecture.setId(1L);
        lecture.setTitle("JavaDev 1");
        lecture.setDescription("Java programming");
        lecture.setLecturer("James Tyson");
        lecture.setAttendanceList(new HashSet<>(Arrays.asList(user)));
    }

    @Test
    void shouldCreateRecordIntoAttendanceListAndReturnUserResponseObject() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.of(lecture));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //when
        UserResponse userResponse = attendanceService
                .createRecordIntoAttendanceListByUserId(13L, 13L);

        //then
        assertNotNull(userResponse);
        assertEquals(Long.valueOf(1L), userResponse.getId());
        assertEquals("Robert", userResponse.getName());
        assertEquals("Mickiewicz", userResponse.getSurname());
        assertEquals("robert.mickiewicz@wp.pl", userResponse.getEmail());
        assertEquals(Integer.valueOf(100300), userResponse.getIndexNumber());
        assertEquals("Second", userResponse.getAcademicYear());
        assertEquals("Civil Engineering", userResponse.getCourseOfStudy());
    }

    @Test
    void shouldThrowWrongUserIdExceptionWhenCallingWrongUserId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.of(lecture));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when

        //then
        assertThrows(WrongUserIdException.class, () -> attendanceService
                .createRecordIntoAttendanceListByUserId(13L, 13L));
    }

    @Test
    void shouldThrowWrongLectureIdExceptionWhenCallingWrongLectureId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //when

        //then
        assertThrows(WrongLectureIdException.class, () -> attendanceService
                .createRecordIntoAttendanceListByUserId(13L, 13L));
    }

    @Test
    void shouldGetAttendanceListFromLectureByLectureId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.of(lecture));

        //when
        Set<UserResponse> usersResponse = attendanceService.getAttendanceListFromLectureId(13L);

        //then
        assertNotNull(usersResponse);
        assertEquals(1, usersResponse.size());
        assertEquals(Long.valueOf(1L), ((UserResponse) (usersResponse.toArray()[0])).getId());
        assertEquals("Robert", ((UserResponse) (usersResponse.toArray()[0])).getName());
    }

    @Test
    void shouldNotGetAttendanceListFromLectureAndThrowWrongLectureIdExceptionWhenIndicateWrongLectureId() {
        //given
        when(lectureRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when

        //then
        assertThrows(WrongLectureIdException.class,
                () -> attendanceService.getAttendanceListFromLectureId(444L));
    }
}