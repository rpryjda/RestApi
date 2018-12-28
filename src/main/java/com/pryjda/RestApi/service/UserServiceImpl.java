package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.entities.UserProfile;
import com.pryjda.RestApi.exceptions.WrongUserEmailException;
import com.pryjda.RestApi.exceptions.WrongUserIdException;
import com.pryjda.RestApi.exceptions.WrongUserIndexNumberException;
import com.pryjda.RestApi.model.request.UserRequest;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.repository.UserProfileRepository;
import com.pryjda.RestApi.repository.UserRepository;
import com.pryjda.RestApi.utils.UserResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> usersResponse = new ArrayList<>();
        for (User item : users) {
            usersResponse.add(UserResponseBuilder.getUserResponseFromUserAndUserProfile(item, item.getUserProfile()));
        }
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
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new WrongUserEmailException("User with e-mail: " + email + " doesn't exist"));

        return UserResponseBuilder.getUserResponseFromUserAndUserProfile(user, user.getUserProfile());
    }

    @Override
    public UserResponse getUserByIndexNumber(int indexNumber) {
        User user = userRepository.findUserByIndexNumber(indexNumber)
                .orElseThrow(() -> new WrongUserIndexNumberException("User with index: " + indexNumber + " doesn't exist"));

        return UserResponseBuilder.getUserResponseFromUserAndUserProfile(user, user.getUserProfile());
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        UserProfile userProfile = mapper.map(userRequest, UserProfile.class);

        user.setUserProfile(userProfile);
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);
        User createdUser = userRepository.save(user);

        return UserResponseBuilder.getUserResponseFromUserAndUserProfile(createdUser, createdUser.getUserProfile());
    }

    @Override
    public boolean updateUser(Long userId, UserRequest userRequest) {

        return userRepository.findById(userId)
                .map(user -> {
                    user.setEmail(userRequest.getEmail());
                    user.setIndexNumber(userRequest.getIndexNumber());
                    user.setPassword(userRequest.getPassword());
                    userProfileRepository.findById(user.getUserProfile().getId())
                            .ifPresent(userProfile -> {
                                userProfile.setName(userRequest.getName());
                                userProfile.setSurname(userRequest.getSurname());
                                userProfile.setAcademicYear(userRequest.getAcademicYear());
                                userProfile.setCourseOfStudy(userRequest.getCourseOfStudy());
                            });
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean updateUserByEmail(String email, UserRequest userRequest) {

        return userRepository.findUserByEmail(email)
                .map(user -> {
                    user.setEmail(userRequest.getEmail());
                    user.setIndexNumber(userRequest.getIndexNumber());
                    user.setPassword(userRequest.getPassword());
                    userProfileRepository.findById(user.getUserProfile().getId())
                            .ifPresent(userProfile -> {
                                userProfile.setName(userRequest.getName());
                                userProfile.setSurname(userRequest.getSurname());
                                userProfile.setAcademicYear(userRequest.getAcademicYear());
                                userProfile.setCourseOfStudy(userRequest.getCourseOfStudy());
                            });
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean updateUserByIndexNumber(int indexNumber, UserRequest userRequest) {
        return userRepository.findUserByIndexNumber(indexNumber)
                .map(user -> {
                    user.setEmail(userRequest.getEmail());
                    user.setIndexNumber(userRequest.getIndexNumber());
                    user.setPassword(userRequest.getPassword());
                    userProfileRepository.findById((user.getUserProfile().getId()))
                            .ifPresent(userProfile -> {
                                userProfile.setName(userRequest.getName());
                                userProfile.setSurname(userRequest.getSurname());
                                userProfile.setAcademicYear(userRequest.getAcademicYear());
                                userProfile.setCourseOfStudy(userRequest.getCourseOfStudy());
                            });
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteUser(Long userId) {

        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean resetPassword(Long userId, String password) {

        return userRepository.findById(userId)
                .map(user -> {
                    user.setPassword(password);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean resetPasswordByEmail(String email, String password) {

        return userRepository.findUserByEmail(email)
                .map(user -> {
                    user.setPassword(password);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean resetPasswordByIndexNumber(int indexNumber, String password) {

        return userRepository.findUserByIndexNumber(indexNumber)
                .map(user -> {
                    user.setPassword(password);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
}
