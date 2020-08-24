package org.ccem.otus.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateAdapter {

  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final String TIME_ZONE = "UTC";

  public static String toISODate(Date date){
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
    return dateFormat.format(date);
  }

  public static String nowToISODate(){
    return toISODate(new Date());
  }

  public static String getDatePlusDays(String dateStr, int days) throws ParseException {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
    calendar.setTime(dateFormat.parse(dateStr));
    calendar.add(Calendar.DATE, days);
    return toISODate(calendar.getTime());
  }

}
