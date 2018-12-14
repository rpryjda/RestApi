package com.pryjda.RestApi.service;

import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;

import java.util.List;

public interface LectureService {

    List<LectureResponse> getLectures();

    LectureResponse createLecture(LectureRequest lectureRequest);

    boolean updateLecture(Long lectureId, LectureRequest lectureRequest);

    boolean deleteLecture(Long lectureId);
}
