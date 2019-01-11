package com.pryjda.RestApi.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pryjda.RestApi.model.validation.order.lectureRequest.CreatingLectureStepNo1;
import com.pryjda.RestApi.model.validation.order.lectureRequest.UpdatingLectureStepNo1;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class LectureRequest {

    @NotEmpty(message = "Title of lecture is required", groups = CreatingLectureStepNo1.class)
    private String title;

    @NotEmpty(message = "Description of lecture is required", groups = CreatingLectureStepNo1.class)
    private String description;

    @NotEmpty(message = "Lecturer is required", groups = CreatingLectureStepNo1.class)
    private String lecturer;

    @NotNull(message = "Time of lecture is required (pattern: 'yyyy-MM-dd HH:mm')", groups = CreatingLectureStepNo1.class)
    @FutureOrPresent(groups = {CreatingLectureStepNo1.class, UpdatingLectureStepNo1.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
}
