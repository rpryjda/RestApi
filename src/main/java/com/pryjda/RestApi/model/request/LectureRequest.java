package com.pryjda.RestApi.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class LectureRequest {
    private String title;
    private String description;
    private String lecturer;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime date;
}
