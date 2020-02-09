package com.github.altergr.meteringdemo.domain.entity;

import lombok.experimental.UtilityClass;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@UtilityClass
public class MonthName {

    public static String of(int month) {
        return Month.of(month).getDisplayName(TextStyle.FULL, Locale.UK);
    }
}
