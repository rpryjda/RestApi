package com.pryjda.RestApi.utils;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.entities.UserProfile;
import com.pryjda.RestApi.model.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceListForLectureServiceTest {

    @InjectMocks
    private AttendanceListForLectureService attendanceList;

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
    void shouldGetAttendanceListForLectureObject() {
        //given

        //when
        Set<UserResponse> usersResponse = attendanceList.getAttendanceListFromLectureObject(lecture);

        //then
        assertNotNull(usersResponse);
        assertEquals(1, usersResponse.size());
        assertEquals(Long.valueOf(1L), ((UserResponse) (usersResponse.toArray()[0])).getId());
        assertEquals("Robert", ((UserResponse) (usersResponse.toArray()[0])).getName());
    }
}