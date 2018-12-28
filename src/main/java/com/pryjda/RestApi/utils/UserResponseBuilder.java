package com.pryjda.RestApi.utils;

import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.entities.UserProfile;
import com.pryjda.RestApi.model.response.UserResponse;

public class UserResponseBuilder {

    public static UserResponse getUserResponseFromUserAndUserProfile(User user, UserProfile userProfile) {
        UserResponse userResponse = new UserResponse();
        if (userProfile != null) {
            userResponse.setName(userProfile.getName());
            userResponse.setSurname(userProfile.getSurname());
            userResponse.setAcademicYear(userProfile.getAcademicYear());
            userResponse.setCourseOfStudy(userProfile.getCourseOfStudy());
        }
        if (user != null) {
            userResponse.setId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setIndexNumber(user.getIndexNumber());
            userResponse.setPassword(user.getPassword());
        }
        return userResponse;
    }
}
