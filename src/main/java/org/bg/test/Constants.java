package org.bg.test;

import java.util.HashMap;

public class Constants {

    public static final String GENERATED_SENSORS_PACKAGE = "org.bg.test.sensors.generated";
    public static final String CSV_RECORDS_PATH = "/home/barakg/IdeaProjects/CsvPlayer/csvFiles/29_12_2022/";
    public static final long INITIAL_TIME_STAMP = 1641052800000L;
    public static final int DELAY_BETWEEN_REPORTS_IN_MILLIS = 1;

    public static final HashMap<String, Integer> DELAY_BETWEEN_REPORTS_FOR_SENSOR = new HashMap<>() {
        {
            put("A", DELAY_BETWEEN_REPORTS_IN_MILLIS);
            put("GPS", DELAY_BETWEEN_REPORTS_IN_MILLIS * 2);
            put("AB", DELAY_BETWEEN_REPORTS_IN_MILLIS * 3);
            put("AIS", DELAY_BETWEEN_REPORTS_IN_MILLIS * 4);
        }
    };

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public static final HashMap<String, String> PROMPT_COLORS = new HashMap<>() {
        {
            put("ERROR", ANSI_RED_BACKGROUND);
            put("AIS", ANSI_BLACK_BACKGROUND);
            put("A", ANSI_BLUE_BACKGROUND);
            put("GPS", ANSI_WHITE_BACKGROUND);
            put("AB", ANSI_GREEN_BACKGROUND);
        }
    };
}
