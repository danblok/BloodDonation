package com.example.blooddonation;

import java.util.regex.Pattern;

public class Validator {

    public static boolean isDateOfBirthValid(String dateOfBirth) {
        Pattern pattern = Pattern.compile("^\\d{2}.\\d{2}.\\d{4}$");
        return pattern.matcher(dateOfBirth).matches();
    }

    public static boolean isSeriesAndNumberValid(String serialNumber) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        return pattern.matcher(serialNumber).matches();
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$",
                Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$");
        return pattern.matcher(password).matches();
    }
}
