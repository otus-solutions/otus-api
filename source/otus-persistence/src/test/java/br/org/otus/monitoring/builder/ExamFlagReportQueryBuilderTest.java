package br.org.otus.monitoring.builder;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ExamFlagReportQueryBuilderTest {
  private static ExamFlagReportQueryBuilder builder = null;
  private static ArrayList<Long> CENTER_RN = new ArrayList();
  private static LinkedList<String> ALL_POSSIBLE_EXAMS = new LinkedList<>();
  private static List<Document> EIS = new ArrayList();

  @Before
  public void setUp() {
    builder = new ExamFlagReportQueryBuilder();
    ALL_POSSIBLE_EXAMS = new LinkedList<>();
    ALL_POSSIBLE_EXAMS.add("CÁLCIO - URINA AMOSTRA ISOLADA");
    ALL_POSSIBLE_EXAMS.add("CREATININA - URINA AMOSTRA ISOLADA");
  }

  @Test
  public void getExamResultsStatusQueryMethod_should_check_in() {
    String expectedQuery = "[{\"$match\":{\"recruitmentNumber\":{\"$in\":[]}}},{\"$project\":{\"recruitmentNumber\":1.0,\"examName\":1.0}},{\"$group\":{\"_id\":\"$recruitmentNumber\",\"exams\":{\"$addToSet\":\"$examName\"}}},{\"$group\":{\"_id\":{},\"participantList\":{\"$push\":{\"recruitmentNumber\":\"$_id\",\"exams\":\"$exams\"}}}},{\"$addFields\":{\"allRns\":[]}},{\"$unwind\":\"$allRns\"},{\"$addFields\":{\"participantFound\":{\"$arrayElemAt\":[{\"$filter\":{\"input\":\"$participantList\",\"as\":\"participant\",\"cond\":{\"$eq\":[\"$$participant.recruitmentNumber\",\"$allRns\"]}}},0.0]}}},{\"$project\":{\"_id\":\"$allRns\",\"exams\":{\"$ifNull\":[\"$participantFound.exams\",[]]}}},{\"$addFields\":{\"headers\":[\"CÁLCIO - URINA AMOSTRA ISOLADA\",\"CREATININA - URINA AMOSTRA ISOLADA\"]}},{\"$unwind\":\"$headers\"},{\"$addFields\":{\"found\":{\"$filter\":{\"input\":\"$exams\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item\",\"$headers\"]}}}}},{\"$addFields\":{\"examInapplicatibityFound\":{\"$filter\":{\"input\":[],\"as\":\"examInnaplicability\",\"cond\":{\"$and\":[{\"$eq\":[\"$$examInnaplicability.recruitmentNumber\",\"$_id\"]},{\"$eq\":[\"$$examInnaplicability.name\",\"$headers\"]}]}}}}},{\"$group\":{\"_id\":\"$_id\",\"filtered\":{\"$push\":{\"$cond\":[{\"$gt\":[{\"$size\":\"$examInapplicatibityFound\"},0.0]},0.0,{\"$cond\":[{\"$gt\":[{\"$size\":\"$found\"},0.0]},1.0,-1.0]}]}}}},{\"$group\":{\"_id\":{},\"index\":{\"$push\":\"$_id\"},\"data\":{\"$push\":\"$filtered\"}}}]";
    assertEquals(expectedQuery, new GsonBuilder().create().toJson(builder.getExamResultsStatusQuery(ALL_POSSIBLE_EXAMS, CENTER_RN, EIS)));
  }
}