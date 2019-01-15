package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.repository.LectureRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    private final AttendanceService attendanceService;

    private static final ModelMapper mapper = new ModelMapper();

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository, AttendanceService attendanceService) {
        this.lectureRepository = lectureRepository;
        this.attendanceService = attendanceService;
    }

    @Override
    public List<LectureResponse> getLectures() {
        List<Lecture> lectures = lectureRepository.findAll();
        List<LectureResponse> lecturesResponse = new ArrayList<>();
        lectures.stream()
                .forEach(item -> {
                    Set<UserResponse> usersResponse = attendanceService.getAttendanceListFromLectureObject(item);
                    LectureResponse lectureResponse = mapper.map(item, LectureResponse.class);
                    lectureResponse.setAttendanceList(usersResponse);
                    lecturesResponse.add(lectureResponse);
                });
        return lecturesResponse;
    }

    @Override
    public LectureResponse getLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    LectureResponse lectureResponse = mapper.map(lecture, LectureResponse.class);
                    Set<UserResponse> usersResponse = attendanceService.getAttendanceListFromLectureObject(lecture);
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
    @Transactional
    public boolean updateLecture(Long lectureId, LectureRequest lectureRequest) {

        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    if (lectureRequest.getDescription() != null) {
                        lecture.setDescription(lectureRequest.getDescription());
                    }
                    if (lectureRequest.getTitle() != null) {
                        lecture.setTitle(lectureRequest.getTitle());
                    }
                    if (lectureRequest.getLecturer() != null) {
                        lecture.setLecturer(lectureRequest.getLecturer());
                    }
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
