package com.group2.foodie.util;

import com.group2.foodie.model.Measurement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {
    public static List<String> getMeasurements() {
        List<String> output = new ArrayList<>(Measurement.values().length);

        int i = 0;
        for (Measurement measurement : Measurement.values()) {
            output.add(i++, measurement.toString());
        }

        return output;
    }

    public static String getStringFromLocalDate(LocalDate localDate) {
        return String.format("%d-%d-%d", localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth());
    }

    public static LocalDate getLocalDateFromString(String string) {
        return LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
