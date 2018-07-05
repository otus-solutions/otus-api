package org.ccem.otus.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ISOStringHandler {

    public static String removeTimePart(String ISOString) {
        Instant instant = Instant.parse(ISOString);
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).truncatedTo(ChronoUnit.DAYS);

        return OffsetDateTime
                .parse(ISOString)
                .truncatedTo(ChronoUnit.DAYS)
                .format(DateTimeFormatter.ISO_DATE_TIME);

    }
}
