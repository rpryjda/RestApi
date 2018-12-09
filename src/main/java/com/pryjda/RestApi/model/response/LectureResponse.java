package com.pryjda.RestApi.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LectureResponse {
    private Long id;
    private String title;
    private String description;
    private String lecturer;
}
