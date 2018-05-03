package org.ccem.otus.model.dataSources.activity;

import org.bson.Document;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceFilters;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceResult;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceStatusHistoryFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
public class ActivityDataSourceTest {
	private static final Long RECRUITMENT_NUMBER = 343545345L;
	private static final String EXPECTED_RESULT = "{ \"$match\" : { \"participantData.recruitmentNumber\" : { \"$numberLong\" : \"343545345\" }, \"surveyForm.surveyTemplate.identity.acronym\" : \"TF\", \"category.name\" : \"C0\" } }";
	private static final String EXPECTED_RESULT_PROJECTION = "{ \"$project\" : { \"_id\" : -1 } }";
	private static final String EXPECTED_RESULT_PROJECTION_WHIT_STATUS_HISTORY = "{ \"$project\" : { \"_id\" : -1, \"statusHistory\" : { \"$slice\" : [\"$statusHistory\", -1, 1] } } }";
	private static final String EXPECTED_RESULT_MATCH_WHIT_STATUS_HISTORY = "{ \"$match\" : { \"statusHistory.name\" : \"FINALIZED\" } }";

	private ActivityDataSource activityDataSource;
	private ActivityDataSourceStatusHistoryFilter activityDataSourceStatusHistoryFilter;
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
		activityDataSourceStatusHistoryFilter = new ActivityDataSourceStatusHistoryFilter();

		Whitebox.setInternalState(activityDataSourceStatusHistoryFilter, "name", "FINALIZED");
		Whitebox.setInternalState(activityDataSourceStatusHistoryFilter, "position", -1);
		Whitebox.setInternalState(activityDataSourceFilters, "statusHistory", activityDataSourceStatusHistoryFilter);
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
}
