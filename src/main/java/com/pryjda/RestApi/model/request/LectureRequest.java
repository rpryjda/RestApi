package com.pryjda.RestApi.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LectureRequest {
    private String title;
    private String description;
    private String lecturer;
}
