package org.ccem.otus.model.dataSources.examResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.dataSources.exam.ExamDataSource;
import org.ccem.otus.model.dataSources.exam.ExamDataSourceFilters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class ExamDataSourceTest {

    private ObjectId objectId;
    private static final String valueObjectId = "5ac4fc27a75a7300ea242aa6";
    private static final Long VALUE_RECRUITMENT_NUMBER = 1063154L;
    private static final String VALUE_EXAM_NAME = "TRIGLICÉRIDES - SANGUE";
    private static final String VALUE_RS = "RS";

    private static final String FILTER_EXAM_NAME = "examName";
    private static final CharSequence FILTER_FIELD_CENTER_ACRONYM = "fieldCenter.acronym";

    private static final String EXPECTED_RESULT_QUERY_ONE = "[Document{{$match=Document{{objectType=ExamResults, examName=TRIGLICÉRIDES - SANGUE, recruitmentNumber=1063154}}}}, Document{{$lookup=Document{{from=exam_sending_lot, localField=examSendingLotId, foreignField=_id, as=sendingLot}}}}, Document{{$match=Document{{sendingLot.fieldCenter.acronym=RS}}}}, Document{{$sort=Document{{sendingLot.realizationDate=1, _id=1}}}}, Document{{$limit=1}}, Document{{$project=Document{{_id=1, examSendingLotId=1, examId=1, objectType=1, aliquotCode=1, examName=1, resultName=1, value=1, aliquotValid=1, releaseDate=1, observations=1, recruitmentNumber=1, sex=1, birthdate=1, sendingLot=1, forcedSave=1}}}}]";
    private static final String EXPECTED_RESULT_QUERY_TWO = "[Document{{$match=Document{{_id=" + valueObjectId + ", objectType=Exam, name=" + VALUE_EXAM_NAME + "}}}}, Document{{$lookup=Document{{from=exam_result, let=Document{{exam_oid=$_id}}, pipeline=[Document{{$match=Document{{recruitmentNumber=" + VALUE_RECRUITMENT_NUMBER + ", aliquotValid=true, $expr=Document{{$eq=[$examId, $$exam_oid]}}}}}}], as=examResults}}}}, Document{{$match=Document{{examResults.recruitmentNumber=Document{{$exists=1}}}}}}]";

    private ExamDataSource examResultDataSource;
    private ExamDataSourceFilters filters;

    @Before
    public void setUp() {
        this.objectId = new ObjectId(valueObjectId);
        this.examResultDataSource = new ExamDataSource();
        this.filters = new ExamDataSourceFilters();
        Whitebox.setInternalState(examResultDataSource, "filters", filters);
        Whitebox.setInternalState(filters, "examName", VALUE_EXAM_NAME);
        Whitebox.setInternalState(filters, "fieldCenter", VALUE_RS);
    }

    @Test
    public void method_buildQuery_should_return_query() {
        ArrayList<Document> query = examResultDataSource.buildQuery(VALUE_RECRUITMENT_NUMBER);

        Assert.assertNotNull(query);
    }

    @Test
    public void method_buildQuery_should_return_query_with_center_and_exam_name() {
        ArrayList<Document> query = examResultDataSource.buildQuery(VALUE_RECRUITMENT_NUMBER);

        assertTrue(query.toString().contains(FILTER_FIELD_CENTER_ACRONYM));
        assertTrue(query.toString().contains(FILTER_EXAM_NAME));
    }

    @Test
    public void method_buildQuery_should_return_query_expected() throws Exception {
        ArrayList<Document> query = examResultDataSource.buildQuery(VALUE_RECRUITMENT_NUMBER);

        assertEquals(EXPECTED_RESULT_QUERY_ONE, query.toString());
    }

    @Test
    public void method_buildQueryToExamResults_should_return_query() {
        ArrayList<Document> query = examResultDataSource.buildQueryToExamResults(this.objectId, VALUE_RECRUITMENT_NUMBER);

        Assert.assertNotNull(query);
    }

    @Test
    public void method_buildQueryToExamResults_should_return_query_with_center_and_exam_name() {
        ArrayList<Document> query = examResultDataSource.buildQueryToExamResults(this.objectId, VALUE_RECRUITMENT_NUMBER);

        assertEquals(EXPECTED_RESULT_QUERY_TWO, query.toString());
    }

}
