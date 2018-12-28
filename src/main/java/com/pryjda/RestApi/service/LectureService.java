package com.pryjda.RestApi.service;

import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LectureService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<LectureResponse> getLectures();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    LectureResponse getLectureById(Long lectureId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    LectureResponse createLecture(LectureRequest lectureRequest);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean updateLecture(Long lectureId, LectureRequest lectureRequest);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean deleteLecture(Long lectureId);
}
