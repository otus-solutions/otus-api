package org.ccem.otus.model.monitoring;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import com.google.gson.*;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MonitoringDataSourceTest {
  public static String BUILD_ACRONYM_MATCH_STAGE_RESULT = "{\"$match\":{\"isDiscarded\":false,\"surveyForm.acronym\":\"CSJ\"}}";
  public static String BUILD_STATUS_HISTORY_DATA_PROJECTION_STAGES_RESULT_1 = "{\"$project\":{\"acronym\":\"$surveyForm.acronym\",\"statusHistory\":{\"$slice\":[\"$statusHistory\",-1]},\"rn\":\"$participantData.recruitmentNumber\"}}";
  public static String BUILD_STATUS_HISTORY_DATA_PROJECTION_STAGES_RESULT_2 = "{\"$project\":{\"acronym\":1,\"status.date\":\"$statusHistory.date\",\"status.name\":\"$statusHistory.name\",\"rn\":1}}";
  public static String BUILD_STATUS_DATA_UNWIND_STAGE_RESULT_1 = "{\"$unwind\":\"$status.date\"}";
  public static String BUILD_STATUS_DATA_UNWIND_STAGE_RESULT_2 = "{\"$unwind\":\"$status.name\"}";
  public static String BUILD_STATUS_DATA_PROJECTION_STAGE_RESULT = "{\"$project\":{\"acronym\":1,\"status.month\":{\"$substr\":[\"$status.date\",5,2]},\"status.year\":{\"$substr\":[\"$status.date\",0,4]},\"status.name\":1,\"rn\":1,\"activityDate\":\"$status.date\",\"_id\":0}}";
  public static String BUILD_FINALIZED_ACTIVITY_MATCH_STAGE_RESULT = "{\"$match\":{\"status.name\":\"FINALIZED\"}}";
  public static String BUILD_PARTICIPANT_LOOKUP_STAGE_RESULT = "{\"$lookup\":{\"from\":\"participant\",\"localField\":\"rn\",\"foreignField\":\"recruitmentNumber\",\"as\":\"fieldCenter\"}}";
  public static String BUILD_EXTRACT_ACRONYM_PROJECTION_STAGE_RESULT = "{\"$project\":{\"acronym\":1,\"rn\":1,\"status\":1,\"fieldCenter\":\"$fieldCenter.fieldCenter.acronym\",\"activityDate\":1}}";
  public static String BUILD_FIELD_CENTER_UNWIND_STAGE_RESULT = "{\"$unwind\":\"$fieldCenter\"}";
  public static String BUILD_RESULTS_GROUP_STAGE_RESULT = "{\"$group\":{\"_id\":{\"fieldCenter\":\"$fieldCenter\",\"acronym\":\"$acronym\",\"month\":\"$status.month\",\"year\":\"$status.year\"},\"sum\":{\"$sum\":1},\"firstActivityDate\":{\"$min\":\"$activityDate\"}}}";
  public static String BUILD_RESULTS_PROJECTION_STAGE_RESULT = "{\"$project\":{\"fieldCenter\":\"$_id.fieldCenter\",\"month\":\"$_id.month\",\"year\":\"$_id.year\",\"acronym\":\"$_id.acronym\",\"sum\":\"$sum\",\"firstActivityDate\":1,\"_id\":0}}";
  public static String BUILD_RESULTS_SORT_STAGE_RESULT = "{\"$sort\":{\"fieldCenter\":1,\"acronym\":1,\"year\":1,\"month\":1,\"sum\":1}}";

  private Gson builder;

  private ArrayList<Document> query;

  @Before
  public void setUp() {
    MonitoringDataSource monitoringDataSource = new MonitoringDataSource();
    this.query = monitoringDataSource.buildQuery("CSJ");
    this.builder = new GsonBuilder().create();
  }

  @Test
  public void method_buildQuery_should_return_instance_of_ArrayList() {
    Assert.assertNotNull(this.query);
  }

  @Test
  public void method_buildAcronymMatchStage_should_add_match_stage_to_position_0_on_array() {
    assertEquals(BUILD_ACRONYM_MATCH_STAGE_RESULT, builder.toJson(this.query.get(0)));
  }

  @Test
  public void method_buildStatusHistoryDataProjectionStages_should_add_projection_stage_to_position_1_on_array() {
    assertEquals(BUILD_STATUS_HISTORY_DATA_PROJECTION_STAGES_RESULT_1, builder.toJson(this.query.get(1)));
  }

  @Test
  public void method_buildStatusHistoryDataProjectionStages_should_add_projection_stage_to_position_2_on_array() {
    assertEquals(BUILD_STATUS_HISTORY_DATA_PROJECTION_STAGES_RESULT_2, builder.toJson(this.query.get(2)));
  }

  @Test
  public void method_buildStatusDataUnwindStage_should_add_unwind_stage_to_position_3_on_array() {
    assertEquals(BUILD_STATUS_DATA_UNWIND_STAGE_RESULT_1, builder.toJson(this.query.get(3)));
  }

  @Test
  public void method_buildStatusDataUnwindStage_should_add_unwind_stage_to_position_4_on_array() {
    assertEquals(BUILD_STATUS_DATA_UNWIND_STAGE_RESULT_2, builder.toJson(this.query.get(4)));
  }

  @Test
  public void method_buildStatusDataProjectionStage_should_add_projection_stage_to_position_5_on_array() {
    assertEquals(BUILD_STATUS_DATA_PROJECTION_STAGE_RESULT, builder.toJson(this.query.get(5)));
  }

  @Test
  public void method_buildFinalizedActivityMatchStage_should_add_match_stage_to_position_6_on_array() {
    assertEquals(BUILD_FINALIZED_ACTIVITY_MATCH_STAGE_RESULT, builder.toJson(this.query.get(6)));
  }

  @Test
  public void method_buildParticipantLookupStage_should_add_lookup_stage_to_position_7_on_array() {
    assertEquals(BUILD_PARTICIPANT_LOOKUP_STAGE_RESULT, builder.toJson(this.query.get(7)));
  }

  @Test
  public void method_buildExtractAcronymProjectionStage_should_add_projection_stage_to_position_8_on_array() {
    assertEquals(BUILD_EXTRACT_ACRONYM_PROJECTION_STAGE_RESULT, builder.toJson(this.query.get(8)));
  }

  @Test
  public void method_buildFieldCenterUnwindStage_should_add_unwind_stage_to_position_9_on_array() {
    assertEquals(BUILD_FIELD_CENTER_UNWIND_STAGE_RESULT, builder.toJson(this.query.get(9)));
  }

  @Test
  public void method_buildResultsGroupStage_should_add_group_stage_to_position_10_on_array() {
    assertEquals(BUILD_RESULTS_GROUP_STAGE_RESULT, builder.toJson(this.query.get(10)));
  }

  @Test
  public void method_buildResultsProjectionStage_should_add_projection_stage_to_position_11_on_array() {
    assertEquals(BUILD_RESULTS_PROJECTION_STAGE_RESULT, builder.toJson(this.query.get(11)));
  }

  @Test
  public void method_buildResultsSortStage_should_add_sort_stage_to_position_12_on_array() {
    assertEquals(BUILD_RESULTS_SORT_STAGE_RESULT, builder.toJson(this.query.get(12)));
  }
}
