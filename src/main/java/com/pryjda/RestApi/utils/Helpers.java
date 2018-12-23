package com.pryjda.RestApi.utils;

public class Helpers {

    public static boolean isNumber(String stringNumber) {
        try {
            Integer.parseInt(stringNumber);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
