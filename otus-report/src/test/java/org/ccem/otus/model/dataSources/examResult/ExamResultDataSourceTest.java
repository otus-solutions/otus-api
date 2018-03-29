package org.ccem.otus.model.dataSources.examResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.exam.ExamDataSource;
import org.ccem.otus.model.dataSources.exam.ExamDataSourceFilters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class ExamResultDataSourceTest {

	private static final Long VALUE_RECRUITMENT_NUMBER = 1063154L;
	private static final String VALUE_EXAM_NAME = "TRIGLICÉRIDES - SANGUE";
	private static final String VALUE_RS = "RS";

	private static final String FILTER_EXAM_NAME = "exam.name";
	private static final CharSequence FILTER_FIELD_CENTER_ACRONYM = "fieldCenter.acronym";

	private static final String EXPECTED_RESULT = "[Document{{$lookup=Document{{from=exam_result, localField=_id, foreignField=examSendingLotId, as=exam}}}}, Document{{$match=Document{{exam.objectType=Exam}}}}, Document{{$match=Document{{exam.name=TRIGLICÉRIDES - SANGUE}}}}, Document{{$match=Document{{exam.recruitmentNumber=1063154}}}}, Document{{$match=Document{{fieldCenter.acronym=RS}}}}, Document{{$sort=Document{{realizationDate=1}}}}, Document{{$limit=1}}]";

	private ExamDataSource examResultDataSource;
	private ExamDataSourceFilters filters;

	@Before
	public void setUp() {
		examResultDataSource = new ExamDataSource();
		filters = new ExamDataSourceFilters();
		Whitebox.setInternalState(filters, "examName", VALUE_EXAM_NAME);
		Whitebox.setInternalState(filters, "fieldCenter", VALUE_RS);
	}

	@Test
	public void method_builtQuery_should_return_query() {
		Whitebox.setInternalState(examResultDataSource, "filters", filters);
		ArrayList<Document> query = examResultDataSource.builtQuery(VALUE_RECRUITMENT_NUMBER);

		Assert.assertNotNull(query);
	}

	@Test
	public void query_should_contains_filters() {
		Whitebox.setInternalState(examResultDataSource, "filters", filters);
		ArrayList<Document> query = examResultDataSource.builtQuery(VALUE_RECRUITMENT_NUMBER);

		assertTrue(query.toString().contains(FILTER_FIELD_CENTER_ACRONYM));
		assertTrue(query.toString().contains(FILTER_EXAM_NAME));
	}

	@Test
	public void method_builtQuery_should_return_query_expected() throws Exception {
		Whitebox.setInternalState(examResultDataSource, "filters", filters);
		ArrayList<Document> query = examResultDataSource.builtQuery(VALUE_RECRUITMENT_NUMBER);

		assertEquals(EXPECTED_RESULT, query.toString());
	}

}
