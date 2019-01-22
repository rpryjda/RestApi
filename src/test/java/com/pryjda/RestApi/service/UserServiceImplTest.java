package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.entities.UserProfile;
import com.pryjda.RestApi.exceptions.WrongUserIdException;
import com.pryjda.RestApi.model.request.UserRequest;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.RoleRepository;
import com.pryjda.RestApi.repository.UserProfileRepository;
import com.pryjda.RestApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserProfile userProfile;
    private UserRequest userRequest;

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

        userRequest = new UserRequest();
        userRequest.setName("Robert");
        userRequest.setSurname("Mickiewicz");
        userRequest.setEmail("robert.mickiewicz@wp.pl");
        userRequest.setPassword("123456");
        userRequest.setIndexNumber(100300);
        userRequest.setAcademicYear("Second");
        userRequest.setCourseOfStudy("Civil Engineering");
    }

    @Test
    void shouldGetListOfUsers() {
        //given
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        //when
        List<UserResponse> responseUsers = userService.getUsers();

        //then
        assertEquals(1, responseUsers.size());
    }

    @Test
    void shouldGetUser() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //when
        UserResponse userResponse = userService.getUser(444L);

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
    void shouldThrowWrongUserIdExceptionWhenCallingWrongId() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when

        //then
        assertThrows(WrongUserIdException.class, () ->
                userService.getUser(13L)
        );
    }

    @Test
    void shouldCreateUserAndReturnUserResponseObject() {
        //given
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleRepository.findAll()).thenReturn(new ArrayList<>());
        when(passwordEncoder.encode(anyString())).thenReturn("1234");

        //when
        UserResponse createdUser = userService.createUser(userRequest);

        //then
        assertEquals(Long.valueOf(1L), createdUser.getId());
        assertEquals("Robert", createdUser.getName());
        assertEquals("Mickiewicz", createdUser.getSurname());
        assertEquals("robert.mickiewicz@wp.pl", createdUser.getEmail());
        assertEquals(Integer.valueOf(100300), createdUser.getIndexNumber());
        assertEquals("Second", createdUser.getAcademicYear());
        assertEquals("Civil Engineering", createdUser.getCourseOfStudy());
    }

    @Test
    void shouldUpdateUserAndReturnTrueIfExistsUserWithIndicatedId() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userProfileRepository.findById(anyLong())).thenReturn(Optional.of(userProfile));

        //when
        boolean result = userService.updateUser(13L, userRequest);

        //then
        assertTrue(result);
    }

    @Test
    void shouldNotUpdateUserAndReturnFalseIfNotExistsUserWithIndicatedId() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        boolean result = userService.updateUser(13L, userRequest);

        //then
        assertFalse(result);
    }

    @Test
    void shouldDeleteUserAndReturnTrue() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(lectureRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        boolean result = userService.deleteUser(13L);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenDeleteUserWhoNotExists() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        boolean result = userService.deleteUser(13L);

        //then
        assertFalse(result);
    }

    @Test
    void shouldResetPasswordForUserAndReturnTrueWhenUserWithIndicatedIdExists() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("1234");

        //when
        boolean result = userService.resetPassword(13L, "new password");

        //then
        assertTrue(result);
    }

    @Test
    void shouldNotResetPasswordAndReturnFalseIfUserWithIndicatedIdNotExists() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("1234");

        //when
        boolean result = userService.resetPassword(444L, "new password");

        //then
        assertNotEquals("new password", user.getPassword());
        assertFalse(result);
    }
}