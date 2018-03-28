package org.ccem.otus.model.dataSources.examResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.exam.ExamResultDataSource;
import org.ccem.otus.model.dataSources.exam.ExamResultDataSourceFilters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

@Ignore
public class ExamResultDataSourceTest {

	private static final Long RECRUITMENT_NUMBER = 1063154L;
	private static final String FIELD_CENTER_ACRONYM = "acronym";

	private static final String EXPECTED_RESULT = "[Document{{$match=Document{{objectType=Exam, name=TRIGLICÉRIDES - SANGUE}}}}, Document{{$lookup=Document{{from=exam_result, localField=_id, foreignField=examId, as=examResults}}}}, Document{{$match=Document{{examResults.recruitmentNumber=1063154}}}}, Document{{$match=Document{{examResults.releaseDate=2018-03-21T17:42:17.205Z}}}}, Document{{$lookup=Document{{from=exam_sending_lot, localField=examSendingLotId, foreignField=_id, as=examSendingLot}}}}, Document{{$match=Document{{examSendingLot.fieldCenter.acronym=RS}}}}]";

	private static final String RESULT_FILTER_EXAM_NAME = "name";
	private static final CharSequence RESULT_FILTER_REALIZATION_DATE = "releaseDate";
	private static final CharSequence RESULT_FILTER_FIELD_CENTER_ACRONYM = "fieldCenter.acronym";

	private static final String VALUE_RS = "RS";

	private ExamResultDataSource examResultDataSource;
	private ExamResultDataSourceFilters filters;

	@Before
	public void setUp() {
		examResultDataSource = new ExamResultDataSource();
		filters = new ExamResultDataSourceFilters();
		// ExamDataSourceFieldCenterFilter fieldCenterFilter = new
		// ExamDataSourceFieldCenterFilter();
		// Whitebox.setInternalState(fieldCenterFilter, FIELD_CENTER_ACRONYM, VALUE_RS);

		Whitebox.setInternalState(filters, "examName", "TRIGLICÉRIDES - SANGUE");
		Whitebox.setInternalState(filters, "releaseDate", "2018-03-21T17:42:17.205Z");
		// Whitebox.setInternalState(filters, "fieldCenter", fieldCenterFilter);
	}

	@Test
	public void method_builtQuery_should_return_query() {
		Whitebox.setInternalState(examResultDataSource, "filters", filters);
		ArrayList<Document> query = examResultDataSource.builtQuery(RECRUITMENT_NUMBER);

		Assert.assertNotNull(query);
	}

	@Test
	public void method_builtQuery_should_return_query_expected() throws Exception {
		Whitebox.setInternalState(examResultDataSource, "filters", filters);
		ArrayList<Document> query = examResultDataSource.builtQuery(RECRUITMENT_NUMBER);

		assertEquals(EXPECTED_RESULT, query.toString());
	}

	@Test
	public void query_should_contains_filters() {
		Whitebox.setInternalState(examResultDataSource, "filters", filters);
		ArrayList<Document> query = examResultDataSource.builtQuery(RECRUITMENT_NUMBER);

		assertTrue(query.toString().contains(RESULT_FILTER_FIELD_CENTER_ACRONYM));
		assertTrue(query.toString().contains(RESULT_FILTER_EXAM_NAME));
		assertTrue(query.toString().contains(RESULT_FILTER_REALIZATION_DATE));
	}
}
