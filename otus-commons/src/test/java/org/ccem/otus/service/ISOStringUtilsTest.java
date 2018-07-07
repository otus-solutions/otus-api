package org.ccem.otus.service;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ISOStringUtilsTest {

    private final static String isoString = "2018-07-07T00:10:33.133Z";
    private final static String roundedDownISOString = "2018-07-07T00:00:00Z";
    private final static String roundedUpISOString = "2018-07-07T23:59:00Z";
    private final static String zeroedTimeISOString = "2018-07-07T00:00:00Z";

    @Test
    public void should_round_down_time_part(){
        List<String> result = ISOStringUtils.handleDateRange(isoString, isoString);
        assertEquals(result.get(0), roundedDownISOString);
    }

    @Test
    public void should_round_up_time_part_from_final_date_range(){
        List<String> result = ISOStringUtils.handleDateRange(isoString, isoString);
        assertEquals(result.get(1), roundedUpISOString);
    }

    @Test
    public void should_around_up_time_part_from_final_date_range(){
        String result = ISOStringUtils.zeroTime(isoString);
        assertEquals(result, zeroedTimeISOString);
    }
}