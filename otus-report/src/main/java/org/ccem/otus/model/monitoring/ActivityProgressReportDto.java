package org.ccem.otus.model.monitoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ActivityProgressReportDto {

  private LinkedList<List<String>> columns;
  private LinkedList<Long> index;
  private LinkedList<LinkedList<Integer>> data;

  public ActivityProgressReportDto(ArrayList<ActivitiesProgressReport> progressReport) {
    this.columns = new LinkedList<>();
    this.index = new LinkedList<>();
    this.data = new LinkedList<>();

    setColumns(progressReport.get(0).getAcronyms());

    progressReport.stream().forEach(report -> {
      index.add(report.getRn());

      addToData(report.getActivities());
    });
  }

  private void setColumns(LinkedList<String> acronyms) {
    acronyms.stream().forEach(acronym -> {
      columns.add(Arrays.asList("C", acronym));
    });
  }

  private void addToData(LinkedList<ActivitiesProgressReport.ActivityFlagReport> flagReports){
    LinkedList<Integer> temp = new LinkedList<>();

    flagReports.stream().forEach(activityFlagReport -> {
      temp.add(activityFlagReport.getStatus());
    });

    data.add(temp);
  }

}
