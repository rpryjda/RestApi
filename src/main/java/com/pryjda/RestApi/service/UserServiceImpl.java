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
import com.pryjda.RestApi.utils.UserResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final RoleRepository roleRepository;

    private final LectureRepository lectureRepository;

    private final PasswordEncoder passwordEncoder;

    private static final ModelMapper mapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder, LectureRepository lectureRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> usersResponse = new ArrayList<>();
        users.stream()
                .forEach(user -> usersResponse.add(UserResponseBuilder.
                        getUserResponseFromUserAndUserProfile(user, user.getUserProfile())));
        return usersResponse;
    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new WrongUserIdException("number: " + userId + " is wrong user id"));
        return UserResponseBuilder.getUserResponseFromUserAndUserProfile(user, user.getUserProfile());
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        UserProfile userProfile = mapper.map(userRequest, UserProfile.class);
        roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equals("ROLE_USER"))
                .forEach(role -> role.getUsers().add(user));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserProfile(userProfile);
        User createdUser = userRepository.save(user);

        return UserResponseBuilder.getUserResponseFromUserAndUserProfile(createdUser, createdUser.getUserProfile());
    }

    @Override
    @Transactional
    public boolean updateUser(Long userId, UserRequest userRequest) {

        return userRepository.findById(userId)
                .map(user -> {
                    if (userRequest.getEmail() != null) {
                        user.setEmail(userRequest.getEmail());
                    }
                    if (userRequest.getIndexNumber() != 0) {
                        user.setIndexNumber(userRequest.getIndexNumber());
                    }
                    userProfileRepository.findById(user.getUserProfile().getId())
                            .ifPresent(userProfile -> {
                                if (userRequest.getName() != null) {
                                    userProfile.setName(userRequest.getName());
                                }
                                if (userRequest.getSurname() != null) {
                                    userProfile.setSurname(userRequest.getSurname());
                                }
                                if (userRequest.getAcademicYear() != null) {
                                    userProfile.setAcademicYear(userRequest.getAcademicYear());
                                }
                                if (userRequest.getCourseOfStudy() != null) {
                                    userProfile.setCourseOfStudy(userRequest.getCourseOfStudy());
                                }
                            });
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteUser(Long userId) {

        return userRepository.findById(userId)
                .map(user -> {
                    lectureRepository.findAll().stream()
                            .forEach(lecture -> lecture.getAttendanceList().remove(user));
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean resetPassword(Long userId, String password) {

        return userRepository.findById(userId)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(password));
                    return true;
                })
                .orElse(false);
    }
}
