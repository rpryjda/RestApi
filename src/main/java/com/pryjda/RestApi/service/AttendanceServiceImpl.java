package com.pryjda.RestApi.service;

import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public boolean createRecordIntoAttendanceListByUserEmail(Long lectureID, String userEmail) {
        return lectureRepository.findById(lectureID)
                .map(lecture -> userRepository.findUserByEmail(userEmail)
                            .map(user -> {
                                lecture.getAttendanceList().add(user);
                                lectureRepository.save(lecture);
                                return true;
                            })
                            .orElse(false))
                .orElse(false);
    }

    @Override
    public boolean createRecordIntoAttendanceListByUserIndexNumber(Long lectureID, int indexNumber) {
        return lectureRepository.findById(lectureID)
                .map(lecture -> userRepository.findUserByIndexNumber(indexNumber)
                        .map(user -> {
                            lecture.getAttendanceList().add(user);
                            lectureRepository.save(lecture);
                            return true;
                        })
                        .orElse(false))
                .orElse(false);
    }
}
