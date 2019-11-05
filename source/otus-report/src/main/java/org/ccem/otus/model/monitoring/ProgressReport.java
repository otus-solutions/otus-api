package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;
import org.ccem.otus.utils.LongAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ProgressReport {

  private LinkedList<List<String>> columns;
  private LinkedList<Long> index;
  private LinkedList<LinkedList<Integer>> data;

  public ProgressReport() {
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

  public static ProgressReport deserialize(String activityProgressReportString) {
    ProgressReport progressReport = ProgressReport.getGsonBuilder().create().fromJson(activityProgressReportString, ProgressReport.class);
    return progressReport;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    builder.serializeNulls();
    return builder;
  }

}