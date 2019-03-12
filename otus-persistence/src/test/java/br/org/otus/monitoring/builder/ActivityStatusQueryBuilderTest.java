package br.org.otus.monitoring.builder;

import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ActivityStatusQueryBuilderTest {
  private static ActivityStatusQueryBuilder builder = null;
  private static final String CENTER = "MG";
  private static LinkedList<String> SURVEY_ACRONYM_LIST = new LinkedList<>();

  @Before
  public void setUp() {
    builder = new ActivityStatusQueryBuilder();
    SURVEY_ACRONYM_LIST = new LinkedList<>();
    SURVEY_ACRONYM_LIST.add("HVSD");
    SURVEY_ACRONYM_LIST.add("PSEC");
    SURVEY_ACRONYM_LIST.add("ABC");
    SURVEY_ACRONYM_LIST.add("DEF");
  }

  @Test
  public void getActivityStatusQueryByCenter() {
    String expectedQuery = "[{\"$match\":{\"participantData.fieldCenter.acronym\":\"MG\",\"isDiscarded\":false}},{\"$project\":{\"_id\":0.0,\"rn\":\"$participantData.recruitmentNumber\",\"acronym\":\"$surveyForm.surveyTemplate.identity.acronym\",\"lastStatus_Date\":{\"$arrayElemAt\":[{\"$slice\":[\"$statusHistory.date\",-1.0]},0.0]},\"lastStatus_Name\":{\"$arrayElemAt\":[{\"$slice\":[\"$statusHistory.name\",-1.0]},0.0]}}},{\"$sort\":{\"lastStatus_Date\":1.0}},{\"$group\":{\"_id\":\"$rn\",\"activities\":{\"$push\":{\"status\":{\"$cond\":[{\"$eq\":[\"$lastStatus_Name\",\"CREATED\"]},-1.0,{\"$cond\":[{\"$eq\":[\"$lastStatus_Name\",\"SAVED\"]},1.0,{\"$cond\":[{\"$eq\":[\"$lastStatus_Name\",\"FINALIZED\"]},2.0,-1.0]}]}]},\"rn\":\"$rn\",\"acronym\":\"$acronym\",\"lastStatus_Date\":\"$lastStatus_Date\"}}}},{\"$addFields\":{\"headers\":[\"HVSD\",\"PSEC\",\"ABC\",\"DEF\"]}},{\"$unwind\":\"$headers\"},{\"$addFields\":{\"activityFound\":{\"$filter\":{\"input\":\"$activities\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item.acronym\",\"$headers\"]}}}}},{\"$group\":{\"_id\":\"$_id\",\"filteredActivities\":{\"$push\":{\"$cond\":[{\"$gt\":[{\"$size\":\"$activityFound\"},0.0]},{\"$arrayElemAt\":[\"$activityFound\",-1.0]},{\"rn\":\"$_id\",\"acronym\":\"$headers\"}]}}}},{\"$group\":{\"_id\":{},\"index\":{\"$push\":\"$_id\"},\"data\":{\"$push\":\"$filteredActivities.status\"}}}]";
    assertEquals(expectedQuery, new GsonBuilder().create().toJson(builder.getActivityStatusQuery(CENTER,SURVEY_ACRONYM_LIST)));
  }

  @Test
  public void getActivityStatusQuery() {
    String expectedQuery = "[{\"$match\":{\"isDiscarded\":false}},{\"$project\":{\"_id\":0.0,\"rn\":\"$participantData.recruitmentNumber\",\"acronym\":\"$surveyForm.surveyTemplate.identity.acronym\",\"lastStatus_Date\":{\"$arrayElemAt\":[{\"$slice\":[\"$statusHistory.date\",-1.0]},0.0]},\"lastStatus_Name\":{\"$arrayElemAt\":[{\"$slice\":[\"$statusHistory.name\",-1.0]},0.0]}}},{\"$sort\":{\"lastStatus_Date\":1.0}},{\"$group\":{\"_id\":\"$rn\",\"activities\":{\"$push\":{\"status\":{\"$cond\":[{\"$eq\":[\"$lastStatus_Name\",\"CREATED\"]},-1.0,{\"$cond\":[{\"$eq\":[\"$lastStatus_Name\",\"SAVED\"]},1.0,{\"$cond\":[{\"$eq\":[\"$lastStatus_Name\",\"FINALIZED\"]},2.0,-1.0]}]}]},\"rn\":\"$rn\",\"acronym\":\"$acronym\",\"lastStatus_Date\":\"$lastStatus_Date\"}}}},{\"$addFields\":{\"headers\":[\"HVSD\",\"PSEC\",\"ABC\",\"DEF\"]}},{\"$unwind\":\"$headers\"},{\"$addFields\":{\"activityFound\":{\"$filter\":{\"input\":\"$activities\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item.acronym\",\"$headers\"]}}}}},{\"$group\":{\"_id\":\"$_id\",\"filteredActivities\":{\"$push\":{\"$cond\":[{\"$gt\":[{\"$size\":\"$activityFound\"},0.0]},{\"$arrayElemAt\":[\"$activityFound\",-1.0]},{\"rn\":\"$_id\",\"acronym\":\"$headers\"}]}}}},{\"$group\":{\"_id\":{},\"index\":{\"$push\":\"$_id\"},\"data\":{\"$push\":\"$filteredActivities.status\"}}}]";
    assertEquals(expectedQuery, new GsonBuilder().create().toJson(builder.getActivityStatusQuery(SURVEY_ACRONYM_LIST)));
  }


}