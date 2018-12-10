package com.pryjda.RestApi.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorMessage {
    private LocalDateTime time;
    private String message;
}
