package com.pryjda.RestApi.shared.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LectureDTO {
    private Long id;
    private String title;
    private String description;
    private String lecturer;
}
