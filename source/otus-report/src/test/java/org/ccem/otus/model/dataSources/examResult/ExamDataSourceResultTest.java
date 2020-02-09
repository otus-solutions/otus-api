package org.ccem.otus.model.dataSources.examResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.model.dataSources.exam.ExamDataSourceResult;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.google.gson.GsonBuilder;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.Observation;
import br.org.otus.examUploader.utils.ObjectIdAdapter;

public class ExamDataSourceResultTest {

  private static final String EXAM_RESULTS = "ExamResults";
  private static final String EXAM_NAME = "COLESTEROL TOTAL E FRAÇÕES - SANGUE";
  private static final String RESULT_NAME = "COLESTEROL NÃO HDL";
  private static final String ALIQUOT_CODE = "364005000";
  private static final String VALUE = "123";
  private static final Long RECRUITMENT_NUMBER = 1063154L;

  private ExamDataSourceResult examDataSourceResult;
  private ExamResult examResult;
  private Observation observation;

  private List<ExamResult> examResults;
  private List<Observation> observations;

  @Before
  public void setUp() {
    this.examDataSourceResult = new ExamDataSourceResult();

    observation = new Observation();
    Whitebox.setInternalState(observation, "objectType", "Observation");
    Whitebox.setInternalState(observation, "name", RESULT_NAME);
    Whitebox.setInternalState(observation, "value", VALUE);

    observations = new ArrayList<>();
    observations.add(observation);

    examResult = new ExamResult();
    Whitebox.setInternalState(examResult, "examSendingLotId", new ObjectId());
    Whitebox.setInternalState(examResult, "examId", new ObjectId());
    Whitebox.setInternalState(examResult, "_id", new ObjectId());
    Whitebox.setInternalState(examResult, "objectType", EXAM_RESULTS);
    Whitebox.setInternalState(examResult, "aliquotCode", ALIQUOT_CODE);
    Whitebox.setInternalState(examResult, "examName", EXAM_NAME);
    Whitebox.setInternalState(examResult, "resultName", RESULT_NAME);
    Whitebox.setInternalState(examResult, "value", VALUE);
    Whitebox.setInternalState(examResult, "aliquotValid", true);
    Whitebox.setInternalState(examResult, "releaseDate", "2018-01-03T13:43:00.000Z");
    Whitebox.setInternalState(examResult, "recruitmentNumber", RECRUITMENT_NUMBER);
    Whitebox.setInternalState(examResult, "sex", Sex.M);

    examResults = new ArrayList<>();
    examResults.add(examResult);

    Whitebox.setInternalState(this.examDataSourceResult, "_id", new ObjectId());
    Whitebox.setInternalState(this.examDataSourceResult, "examSendingLotId", new ObjectId());
    Whitebox.setInternalState(this.examDataSourceResult, "objectType", EXAM_RESULTS);
    Whitebox.setInternalState(this.examDataSourceResult, "name", EXAM_NAME);
    Whitebox.setInternalState(this.examDataSourceResult, "examResults", examResults);
    Whitebox.setInternalState(this.examDataSourceResult, "observations", observations);
  }

  @Test
  public void method_serialize_should_return_string() {

    Assert.assertTrue(ExamDataSourceResult.serialize(this.examDataSourceResult) instanceof String);
  }

  @Test
  public void method_serialize_should_return_string_with_correct_values() {
    String result = buildExamDataSourceResultToJson();

    Assert.assertEquals(result, ExamDataSourceResult.serialize(this.examDataSourceResult));
  }

  @Test
  public void method_deserialize_should_return_instance_of_ExamDataSourceResult() {
    String examDataSourceResult = buildExamDataSourceResultToJson();

    Assert.assertTrue(ExamDataSourceResult.deserialize(examDataSourceResult) instanceof ExamDataSourceResult);
  }

  private String buildExamDataSourceResultToJson() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());

    return builder.create().toJson(this.examDataSourceResult);
  }

}
