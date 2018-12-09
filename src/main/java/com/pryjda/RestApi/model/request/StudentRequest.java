package com.pryjda.RestApi.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String academicYear;
    private String courseOfStudy;
    private int indexNumber;
}
