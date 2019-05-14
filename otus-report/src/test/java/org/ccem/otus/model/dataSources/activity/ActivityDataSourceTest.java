package org.ccem.otus.model.dataSources.activity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

import java.util.ArrayList;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class ActivityDataSourceTest {
  private static final Long RECRUITMENT_NUMBER = 343545345L;
  private static final String EXPECTED_RESULT = "{ \"$match\" : { \"participantData.recruitmentNumber\" : { \"$numberLong\" : \"343545345\" }, \"surveyForm.acronym\" : \"TF\", \"category.name\" : \"C0\", \"isDiscarded\" : false } }";
  private static final String EXPECTED_RESULT_PROJECTION = "{ \"$project\" : { \"_id\" : -1, \"statusHistory\" : 1, \"mode\" : 1 } }";

  private static final String EXPECTED_RESULT_PROJECTION_WHIT_STATUS_HISTORY = "{ \"$project\" : { \"_id\" : -1, \"statusHistory\" : 1, \"statusHistoryPosition\" : { \"$slice\" : [\"$statusHistory\", -1, 1] }, \"mode\" : 1 } }";
  private static final String EXPECTED_RESULT_MATCH_WHIT_STATUS_HISTORY = "{ \"$match\" : { \"statusHistoryPosition.name\" : \"FINALIZED\" } }";

  private static final String EXPECTED_RESULT_PROJECTION_WHITOUT_POSITION_STATUS_HISTORY = "{ \"$project\" : { \"_id\" : -1, \"statusHistory\" : 1, \"mode\" : 1 } }";
  private static final String EXPECTED_RESULT_MATCH_WHITOUT_POSITION_STATUS_HISTORY = "{ \"$match\" : { \"statusHistory.name\" : \"FINALIZED\" } }";

  private ActivityDataSource activityDataSource;
  private ActivityDataSourceStatusHistoryFilter filter;
  private ActivityDataSourceFilters activityDataSourceFilters;
  private ActivityDataSource activityDataSourceAddResult = spy(new ActivityDataSource());

  @Before
  public void setUp() {
    activityDataSourceFilters = new ActivityDataSourceFilters();
    activityDataSource = new ActivityDataSource();

    Whitebox.setInternalState(activityDataSourceFilters, "acronym", "TF");
    Whitebox.setInternalState(activityDataSourceFilters, "category", "C0");
  }

  @Test
  public void method_builtQuery() {
    Whitebox.setInternalState(activityDataSource, "filters", activityDataSourceFilters);
    ArrayList<Document> query = activityDataSource.buildQuery((long) 343545345);

    assertEquals(EXPECTED_RESULT, query.get(0).toJson());
    assertEquals(EXPECTED_RESULT_PROJECTION, query.get(1).toJson());
  }

  @Test
  public void method_builtQuery_with_StatusHistoryFilter() {
    filter = new ActivityDataSourceStatusHistoryFilter();

    Whitebox.setInternalState(filter, "name", "FINALIZED");
    Whitebox.setInternalState(filter, "position", -1);
    Whitebox.setInternalState(activityDataSourceFilters, "statusHistory", filter);
    Whitebox.setInternalState(activityDataSource, "filters", activityDataSourceFilters);

    ArrayList<Document> query = activityDataSource.buildQuery(RECRUITMENT_NUMBER);

    assertEquals(EXPECTED_RESULT, query.get(0).toJson());
    assertEquals(EXPECTED_RESULT_PROJECTION_WHIT_STATUS_HISTORY, query.get(1).toJson());
    assertEquals(EXPECTED_RESULT_MATCH_WHIT_STATUS_HISTORY, query.get(2).toJson());
  }

  @Test
  public void method_add_result() throws Exception {
    ActivityDataSourceResult activityDataSourceResult = new ActivityDataSourceResult();
    activityDataSourceAddResult.addResult(activityDataSourceResult);

    verifyPrivate(activityDataSourceAddResult, times(1)).invoke("addResult", activityDataSourceResult);
  }

  @Test
  public void method_builtQuery_without_position_StatusHistoryFilter() {
    filter = new ActivityDataSourceStatusHistoryFilter();

    Whitebox.setInternalState(filter, "name", "FINALIZED");
    Whitebox.setInternalState(activityDataSourceFilters, "statusHistory", filter);
    Whitebox.setInternalState(activityDataSource, "filters", activityDataSourceFilters);

    ArrayList<Document> query = activityDataSource.buildQuery(RECRUITMENT_NUMBER);

    assertEquals(EXPECTED_RESULT, query.get(0).toJson());
    assertEquals(EXPECTED_RESULT_PROJECTION_WHITOUT_POSITION_STATUS_HISTORY, query.get(1).toJson());
    assertEquals(EXPECTED_RESULT_MATCH_WHITOUT_POSITION_STATUS_HISTORY, query.get(2).toJson());
  }

}
