package com.pryjda.RestApi.shared.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String academicYear;
    private String courseOfStudy;
    private int indexNumber;
}
