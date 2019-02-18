package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ActivityProgressReportDto {

  private LinkedList<List<String>> columns;
  private LinkedList<Long> index;
  private LinkedList<LinkedList<Integer>> data;

  public ActivityProgressReportDto(ArrayList<ActivitiesProgressReport> progressReport,LinkedList<String> surveys) {
    this.columns = new LinkedList<>();
    this.index = new LinkedList<>();
    this.data = new LinkedList<>();
  }

  public void setColumns(LinkedList<String> acronyms) {
    this.columns = new LinkedList<>();
    acronyms.stream().forEach(acronym -> {
      columns.add(Arrays.asList("C", acronym));
    });
  }

  public static ActivityProgressReportDto deserialize(String activityProgressReportString) {
    ActivityProgressReportDto activityProgressReportDto = ActivityProgressReportDto.getGsonBuilder().create().fromJson(activityProgressReportString, ActivityProgressReportDto.class);
    return activityProgressReportDto;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();
    return builder;
  }

}