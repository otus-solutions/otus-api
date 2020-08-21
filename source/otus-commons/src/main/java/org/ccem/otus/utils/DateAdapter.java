package org.ccem.otus.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateAdapter {

  public static String getNowAsISODate(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    sdf.setTimeZone(TimeZone.getTimeZone("CET"));
    return sdf.format(new Date());
  }

}
