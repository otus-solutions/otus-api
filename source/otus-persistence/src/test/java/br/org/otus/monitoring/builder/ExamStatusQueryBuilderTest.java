package br.org.otus.monitoring.builder;

import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ExamStatusQueryBuilderTest {
  private static ExamStatusQueryBuilder builder = null;
  private static final Long RN = Long.valueOf(7016098);
  private static LinkedList<String> EXAM_NAME_LIST = new LinkedList<>();

  @Before
  public void setUp() {
    builder = new ExamStatusQueryBuilder();
    EXAM_NAME_LIST = new LinkedList<>();
    EXAM_NAME_LIST.add("CÁLCIO - URINA AMOSTRA ISOLADA");
    EXAM_NAME_LIST.add("CREATININA - URINA AMOSTRA ISOLADA");
  }

  @Test
  public void getExamNameMethod_should_check_in() {
    String expectedQuery = "[{\"$match\":{\"recruitmentNumber\":7016098.0}},{\"$group\":{\"_id\":{\"examName\":\"$examName\",\"examId\":\"$examId\"}}},{\"$group\":{\"_id\":{\"examName\":\"$_id.examName\"},\"quantity\":{\"$sum\":1.0}}},{\"$group\":{\"_id\":{},\"exams\":{\"$push\":{\"name\":\"$_id.examName\",\"quantity\":\"$quantity\"}}}},{\"$addFields\":{\"allExams\":[\"CÁLCIO - URINA AMOSTRA ISOLADA\",\"CREATININA - URINA AMOSTRA ISOLADA\"]}},{\"$unwind\":\"$allExams\"},{\"$addFields\":{\"examFound\":{\"$arrayElemAt\":[{\"$filter\":{\"input\":\"$exams\",\"as\":\"exam\",\"cond\":{\"$eq\":[\"$$exam.name\",\"$allExams\"]}}},0.0]}}},{\"$project\":{\"name\":\"$allExams\",\"quantity\":{\"$cond\":[{\"$ifNull\":[\"$examFound\",false]},\"$examFound.quantity\",0.0]}}},{\"$lookup\":{\"from\":\"exam_inapplicability\",\"let\":{\"name\":\"$name\",\"rn\":\"$recruitmentNumber\"},\"pipeline\":[{\"$match\":{\"$expr\":{\"$and\":[{\"$eq\":[\"$name\",\"$$name\"]},{\"$eq\":[\"$recruitmentNumber\",7016098.0]}]}}},{\"$project\":{\"_id\":0.0,\"name\":0.0,\"recruitmentNumber\":0.0}}],\"as\":\"examInapplicability\"}},{\"$group\":{\"_id\":{},\"participantExams\":{\"$push\":{\"name\":\"$name\",\"quantity\":\"$quantity\",\"doesNotApply\":{\"$arrayElemAt\":[\"$examInapplicability\",0.0]}}}}}]";
    assertEquals(expectedQuery, new GsonBuilder().create().toJson(builder.getExamQuery(RN, EXAM_NAME_LIST)));
  }

  @Test
  public void getExamInapplicabilityQueryMethod_should_check_in() {
    String expectedQuery = "[{\"$match\":{\"recruitmentNumber\":7016098.0}},{\"$group\":{\"_id\":\"\",\"inapplicabilityList\":{\"$push\":{\"name\":\"$name\",\"observation\":\"$observation\"}}}},{\"$addFields\":{\"allExams\":[\"CÁLCIO - URINA AMOSTRA ISOLADA\",\"CREATININA - URINA AMOSTRA ISOLADA\"]}},{\"$unwind\":\"$allExams\"},{\"$project\":{\"name\":\"$allExams\",\"quantity\":{\"$toInt\":\"0\"},\"examInapplicability\":{\"$filter\":{\"input\":\"$inapplicabilityList\",\"as\":\"inapplicability\",\"cond\":{\"$eq\":[\"$$inapplicability.name\",\"$allExams\"]}}}}},{\"$group\":{\"_id\":{},\"participantExams\":{\"$push\":{\"name\":\"$name\",\"quantity\":\"$quantity\",\"doesNotApply\":{\"$arrayElemAt\":[\"$examInapplicability\",0.0]}}}}}]";
    assertEquals(expectedQuery, new GsonBuilder().create().toJson(builder.getExamInapplicabilityQuery(RN, EXAM_NAME_LIST)));
  }
}
