package org.ccem.otus.service;

import br.org.otus.laboratory.project.exam.examInapplicability.persistence.ExamInapplicabilityDao;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.model.monitoring.ProgressReport;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.*;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class MonitoringServiceBeanTest {

  private static final ArrayList<String> LIST_ACRONYMS_CENTERS = new ArrayList<>();
  private static final String CENTER = "RS";
  private static final Long RN = Long.valueOf(7016098);
  private static Document ACTIVITY_INAPPLICABILITY = new Document();
  private static LinkedList<String> SURVEY_ACRONYM_LIST = new LinkedList<>();
  private static String ACTIVITIES_PROGRESS_REPORT_JSON_DTO = "{\"columns\":[[\"C\",\"HVSD\"],[\"C\",\"PSEC\"],[\"C\",\"ABC\"],[\"C\",\"DEF\"]],\"index\":[5113372,5113371],\"data\":[[null,null,2,2],[2,2,null,null]]}";
  private static List<Bson> PIPELINE;

  private static final FieldCenter fieldCenter = new FieldCenter();
  private static final Long GOAL = (long) 3025L;

  @InjectMocks
  private MonitoringServiceBean monitoringServiceBean;

  @Mock
  private MonitoringCenter monitoringCenter;

  @Mock
  private FieldCenterDao fieldCenterDao;

  @Mock
  private ParticipantDao participantDao;

  @Mock
  private SurveyDao surveyDao;

  @Mock
  private ActivityFlagReportDao activityFlagReportDao;

  @Mock
  private LaboratoryProgressDao laboratoryProgressDao;

  @Mock
  private ActivityInapplicabilityDao activityInapplicabilityDao;

  @Mock
  private ExamInapplicabilityDao examInapplicabilityDao;

  @Mock
  private ExamMonitoringDao examMonitoringDao;

  @Mock
  private AggregateIterable<Document> result;

  @Before
  public void setUp() throws DataNotFoundException {

    fieldCenter.setAcronym("BA");
    fieldCenter.setName("Bahia");
    fieldCenter.setBackgroundColor("rgba(255, 99, 132, 0.2)");
    fieldCenter.setBorderColor("rgba(255, 99, 132, 1)");

    LIST_ACRONYMS_CENTERS.add(fieldCenter.getAcronym());
    when(fieldCenterDao.listAcronyms()).thenReturn(LIST_ACRONYMS_CENTERS);
    when(fieldCenterDao.fetchByAcronym(fieldCenter.getAcronym())).thenReturn(fieldCenter);
    when(participantDao.countParticipantActivities(fieldCenter.getAcronym())).thenReturn(GOAL);

    SURVEY_ACRONYM_LIST = new LinkedList<>();
    SURVEY_ACRONYM_LIST.add("HVSD");
    SURVEY_ACRONYM_LIST.add("PSEC");
    SURVEY_ACRONYM_LIST.add("ABC");
    SURVEY_ACRONYM_LIST.add("DEF");
    when(surveyDao.listAcronyms()).thenReturn(SURVEY_ACRONYM_LIST);

  }

  @Test
  public void method_getParticipantExams_should_call_examMonitoringDao_getParticipantExams() throws DataNotFoundException{
    monitoringServiceBean.getParticipantExams(RN);
    verify(examMonitoringDao,times(1)).getParticipantExams(RN);
  }

  @Test
  public void method_setExamInapplicability_should_call_examInapplicabilityDao_update() {
    monitoringServiceBean.setExamInapplicability(anyObject());
    verify(examInapplicabilityDao,times(1)).update(anyObject());
  }

  @Test
  public void method_deleteExamInapplicability_should_call_examInapplicabilityDao_delete() {
    monitoringServiceBean.deleteExamInapplicability(anyObject());
    verify(examInapplicabilityDao,times(1)).delete(anyObject());
  }

  @Test
  public void method_get_monitoring_centers_with_goals() throws ValidationException, DataNotFoundException {
    ArrayList<MonitoringCenter> response = monitoringServiceBean.getMonitoringCenter();
    assertTrue(response.size() > 0);
    assertEquals(fieldCenter.getName(), response.get(0).getName());
    assertEquals(fieldCenter.getBackgroundColor(), response.get(0).getBackgroundColor());
    assertEquals(fieldCenter.getBorderColor(), response.get(0).getBorderColor());
    assertEquals(GOAL, response.get(0).getGoal());
  }

//  @Test
//  public void method_get_activities_progress_should_padronize_the_result_array_with_the_survey_list() throws DataNotFoundException {
//    when(activityInapplicabilityDao.aggregate(any())).thenReturn(result);
//    when(activityFlagReportDao.getActivitiesProgressReport(CENTER,SURVEY_ACRONYM_LIST,ACTIVITY_INAPPLICABILITY)).thenReturn(ACTIVITY_INAPPLICABILITY);
//    ProgressReport progressReport = monitoringServiceBean.getActivitiesProgress(CENTER);
//    System.out.print(progressReport);
//    verify(activityInapplicabilityDao,times(1)).aggregate(PIPELINE);
//    verify(activityFlagReportDao,times(1)).getActivitiesProgressReport(CENTER,SURVEY_ACRONYM_LIST,ACTIVITY_INAPPLICABILITY);
//  }

  @Test
  public void method_getDataOrphanByExams_should_call_laboratoryProgressDao_getDataOrphanByExams() throws DataNotFoundException {
    monitoringServiceBean.getDataOrphanByExams();
    verify(laboratoryProgressDao,times(1)).getDataOrphanByExams();
  }

  @Test
  public void method_getDataQuantitativeByTypeOfAliquots_should_call_laboratoryProgressDao_getDataQuantitativeByTypeOfAliquots() throws DataNotFoundException {
    monitoringServiceBean.getDataQuantitativeByTypeOfAliquots(CENTER);
    verify(laboratoryProgressDao,times(1)).getDataQuantitativeByTypeOfAliquots(CENTER);
  }

  @Test
  public void method_getDataOfPendingResultsByAliquot_should_call_laboratoryProgressDao_getDataOfPendingResultsByAliquot() throws DataNotFoundException {
    monitoringServiceBean.getDataOfPendingResultsByAliquot(CENTER);
    verify(laboratoryProgressDao,times(1)).getDataOfPendingResultsByAliquot(CENTER);
  }

  @Test
  public void method_getDataOfStorageByAliquot_should_call_laboratoryProgressDao_getDataOfStorageByAliquot() throws DataNotFoundException {
    monitoringServiceBean.getDataOfStorageByAliquot(CENTER);
    verify(laboratoryProgressDao,times(1)).getDataOfStorageByAliquot(CENTER);
  }

  @Test
  public void method_getDataByExam_should_call_laboratoryProgressDao_getDataByExam() throws DataNotFoundException {
    monitoringServiceBean.getDataByExam(CENTER);
    verify(laboratoryProgressDao,times(1)).getDataByExam(CENTER);
  }

  @Test
  public void method_getDataToCSVOfPendingResultsByAliquots_should_call_laboratoryProgressDao_getDataToCSVOfPendingResultsByAliquots() throws DataNotFoundException {
    monitoringServiceBean.getDataToCSVOfPendingResultsByAliquots(CENTER);
    verify(laboratoryProgressDao,times(1)).getDataToCSVOfPendingResultsByAliquots(CENTER);
  }

  @Test
  public void method_getDataToCSVOfOrphansByExam_should_call_laboratoryProgressDao_getDataToCSVOfOrphansByExam() throws DataNotFoundException {
    monitoringServiceBean.getDataToCSVOfOrphansByExam();
    verify(laboratoryProgressDao,times(1)).getDataToCSVOfOrphansByExam();
  }

}
