package com.pryjda.RestApi.service;

public interface SecurityService {

    boolean isIdOfLoggedUser(Long id);

    boolean hasNotTakenPlaceYet(Long lectureId);
}
