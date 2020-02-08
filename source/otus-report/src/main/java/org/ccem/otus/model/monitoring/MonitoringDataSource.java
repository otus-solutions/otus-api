package org.ccem.otus.model.monitoring;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

import java.util.ArrayList;
import java.util.Arrays;

public class MonitoringDataSource extends ReportDataSource<MonitoringDataSourceResult> {

  public static final String COLLECTION_NAME = "activity";
  public static final String RN_PATH = "$participantData.recruitmentNumber";
  public static final String ACRONYM_PATH = "$surveyForm.acronym";
  public static final String ACRONYM = "acronym";
  public static final String PROJECT = "$project";
  public static final String MATCH = "$match";
  public static final String RN = "rn";
  public static final String UNWIND = "$unwind";
  public static final String FIELD_CENTER_PATH = "$fieldCenter.fieldCenter.acronym";

  public static final String DISCARDED_PATH = "isDiscarded";

  @Override
  public void addResult(MonitoringDataSourceResult result) {

  }

  @Override
  public ArrayList<Document> buildQuery(Long recruitmentNumber) {
    ArrayList<Document> query = new ArrayList<>();

    return null;
  }

  public ArrayList<Document> buildQuery(String acronym) {
    ArrayList<Document> query = new ArrayList<>();
    this.buildAcronymMatchStage(query, acronym);
    this.buildStatusHistoryDataProjectionStages(query);
    this.buildStatusDataUnwindStage(query);
    this.buildStatusDataProjectionStage(query);
    this.buildFinalizedActivityMatchStage(query);
    this.buildParticipantLookupStage(query);
    this.buildExtractAcronymProjectionStage(query);
    this.buildFieldCenterUnwindStage(query);
    this.buildResultsGroupStage(query);
    this.buildResultsProjectionStage(query);
    this.buildResultsSortStage(query);
    return query;
  }

  private void buildAcronymMatchStage(ArrayList<Document> query, String acronym) {
    Document match = new Document(MATCH,
      new Document(DISCARDED_PATH, false).append("surveyForm.acronym", acronym));
    query.add(match);
  }

  private void buildStatusHistoryDataProjectionStages(ArrayList<Document> query) {
    Document project = new Document(PROJECT,
      new Document(ACRONYM, ACRONYM_PATH)
        .append("statusHistory", new Document("$slice", Arrays.asList("$statusHistory", -1))).append(RN, RN_PATH));
    query.add(project);

    Document project2 = new Document(PROJECT, new Document(ACRONYM, 1).append("status.date", "$statusHistory.date")
      .append("status.name", "$statusHistory.name").append(RN, 1));
    query.add(project2);
  }

  private void buildStatusDataUnwindStage(ArrayList<Document> query) {
    Document unwind = new Document(UNWIND, "$status.date");
    query.add(unwind);

    Document unwind2 = new Document(UNWIND, "$status.name");
    query.add(unwind2);
  }

  private void buildStatusDataProjectionStage(ArrayList<Document> query) {
    Document project3 = new Document(PROJECT,
      new Document(ACRONYM, 1).append("status.month", new Document("$substr", Arrays.asList("$status.date", 5, 2)))
        .append("status.year", new Document("$substr", Arrays.asList("$status.date", 0, 4)))
        .append("status.name", 1).append(RN, 1).append("activityDate", "$status.date")
        .append("_id", 0));
    query.add(project3);
  }

  private void buildFinalizedActivityMatchStage(ArrayList<Document> query) {
    Document match = new Document(MATCH, new Document("status.name", "FINALIZED"));
    query.add(match);
  }

  private void buildParticipantLookupStage(ArrayList<Document> query) {
    Document lookup = new Document("$lookup", new Document("from", "participant").append("localField", "rn")
      .append("foreignField", "recruitmentNumber").append("as", "fieldCenter"));
    query.add(lookup);
  }

  private void buildExtractAcronymProjectionStage(ArrayList<Document> query) {
    Document project4 = new Document(PROJECT, new Document(ACRONYM, 1).append(RN, 1)
      .append("status", 1).append("fieldCenter", FIELD_CENTER_PATH).append("activityDate", 1));
    query.add(project4);
  }

  private void buildFieldCenterUnwindStage(ArrayList<Document> query) {
    Document unwind3 = new Document(UNWIND, "$fieldCenter");
    query.add(unwind3);
  }

  private void buildResultsGroupStage(ArrayList<Document> query) {
    Document group = new Document("$group",
      new Document("_id",
        new Document("fieldCenter", "$fieldCenter").append(ACRONYM, "$acronym").append("month", "$status.month")
          .append("year", "$status.year")).append("sum", new Document("$sum", 1)).append("firstActivityDate",
        new Document("$min", "$activityDate")));
    query.add(group);
  }

  private void buildResultsProjectionStage(ArrayList<Document> query) {
    Document project5 = new Document(PROJECT,
      new Document("fieldCenter", "$_id.fieldCenter").append("month", "$_id.month").append("year", "$_id.year")
        .append(ACRONYM, "$_id.acronym").append("sum", "$sum").append("firstActivityDate", 1).append("_id", 0));
    query.add(project5);
  }

  private void buildResultsSortStage(ArrayList<Document> query) {
    Document sort = new Document("$sort",
      new Document("fieldCenter", 1).append(ACRONYM, 1).append("year", 1).append("month", 1).append("sum", 1));
    query.add(sort);
  }

}
