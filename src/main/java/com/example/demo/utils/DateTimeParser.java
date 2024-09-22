package com.example.demo.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    public static Date parseDate(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateTime, formatter);
        System.out.println("LocalDate: " + localDate);
        return Date.valueOf(localDate);
    }

    public static Date parseDateTime(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate localDate = LocalDate.parse(dateTime, formatter);
        System.out.println("LocalDate: " + localDate);
        return Date.valueOf(localDate);
    }

    public static String parseDateToString(Date dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.toLocalDate().format(formatter);
    }

    public static String parseDateTimeToString(Date dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.toLocalDate().format(formatter);
    }

}
