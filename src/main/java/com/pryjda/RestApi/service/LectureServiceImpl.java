package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.repository.LectureRepository;

import com.pryjda.RestApi.utils.UserResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public List<LectureResponse> getLectures() {
        List<Lecture> lectures = lectureRepository.findAll();
        List<LectureResponse> lecturesResponse = new ArrayList<>();
        for (Lecture item : lectures) {
            Set<UserResponse> usersResponse = getAttendanceList(item);
            LectureResponse lectureResponse = mapper.map(item, LectureResponse.class);
            lectureResponse.setAttendanceList(usersResponse);
            lecturesResponse.add(lectureResponse);
        }
        return lecturesResponse;
    }

    private Set<UserResponse> getAttendanceList(Lecture lecture) {
        Set<User> users = lecture.getAttendanceList();
        Set<UserResponse> usersResponse = new TreeSet<>((x, y) -> (int) (x.getId() - y.getId()));
        for (User itemUser : users) {
            usersResponse.add(UserResponseBuilder
                    .getUserResponseFromUserAndUserProfile(itemUser, itemUser.getUserProfile()));
        }
        return usersResponse;
    }

    @Override
    public LectureResponse getLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    LectureResponse lectureResponse = mapper.map(lecture, LectureResponse.class);
                    Set<UserResponse> usersResponse = getAttendanceList(lecture);
                    lectureResponse.setAttendanceList(usersResponse);
                    return lectureResponse;
                })
                .orElseThrow(() -> new WrongLectureIdException("number: " + lectureId + " is wrong lecture id"));
    }

    @Override
    public LectureResponse createLecture(LectureRequest lectureRequest) {
        Lecture lecture = mapper.map(lectureRequest, Lecture.class);
        Lecture createdLecture = lectureRepository.save(lecture);
        LectureResponse createdLectureResponse = mapper.map(createdLecture, LectureResponse.class);
        return createdLectureResponse;
    }

    @Override
    public boolean updateLecture(Long lectureId, LectureRequest lectureRequest) {

        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    lecture.setDescription(lectureRequest.getDescription());
                    lecture.setTitle(lectureRequest.getTitle());
                    lecture.setLecturer(lectureRequest.getLecturer());
                    lectureRepository.save(lecture);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteLecture(Long lectureId) {

        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    lectureRepository.deleteById(lectureId);
                    return true;
                })
                .orElse(false);
    }
}
