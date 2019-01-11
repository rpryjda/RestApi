package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.entities.UserProfile;
import com.pryjda.RestApi.exceptions.WrongUserIdException;
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
}
