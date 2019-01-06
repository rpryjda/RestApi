package com.pryjda.RestApi.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class LectureResponse {
    private Long id;
    private String title;
    private String description;
    private String lecturer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    @JsonIgnoreProperties({
            "name", "surname", "email", "password", "academicYear", "courseOfStudy", "indexNumber"})
    private Set<UserResponse> attendanceList;
}
