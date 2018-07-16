package org.ccem.otus.service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ISOStringUtils {

    public static String zeroTime(String ISOString) {
        return OffsetDateTime
                .parse(ISOString)
                .truncatedTo(ChronoUnit.DAYS)
                .format(DateTimeFormatter.ISO_DATE_TIME);

    }

    public static List<String> handleDateRange(String initialISOString, String finalISOString) {

        ArrayList<String> range = new ArrayList<>(2);

        range.add(OffsetDateTime
                .parse(initialISOString)
                .truncatedTo(ChronoUnit.DAYS)
                .format(DateTimeFormatter.ISO_DATE_TIME));

        range.add(OffsetDateTime
                .parse(finalISOString)
                .plusDays(1)
                .truncatedTo(ChronoUnit.DAYS)
                .minusMinutes(1)
                .format(DateTimeFormatter.ISO_DATE_TIME));

        return range;
    }

    private String isoToISO(String ISOString) {
        return OffsetDateTime
                .parse(ISOString)
                .truncatedTo(ChronoUnit.DAYS)
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
