package com.example.blooddonation;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static Date parseDate(String date, String pattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return formatter.parse(date);
    }

    public static String dateToString(Date date, String pattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return formatter.format(date);
    }

    public static String getTrimmedValue(TextView textView) {
        return textView.getText().toString().trim();
    }

}
