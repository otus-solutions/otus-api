package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.monitoring.ActivitiesProgressReport;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;
import org.ccem.otus.persistence.FlagReportDao;
import org.ccem.otus.persistence.SurveyDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class MonitoringServiceBeanTest {

  private static final ArrayList<String> LIST_ACRONYMS_CENTERS = new ArrayList<>();
  private static ArrayList<String> SURVEY_ACRONYM_LIST = new ArrayList<>();
  private static final ArrayList<ActivitiesProgressReport> PROGRESS_REPORT_LIST = new ArrayList<>();
  private static final String reportJson1 = "{\n" +
    "    \"activities\": [\n" +
    "    {\n" +
    "      \"rn\": 5113372,\n" +
    "      \"acronym\": \"ABC\",\n" +
    "      \"status\": 2\n" +
    "    },\n" +
    "    {\n" +
    "      \"rn\": 5113372,\n" +
    "      \"acronym\": \"DEF\",\n" +
    "      \"status\": 2\n" +
    "    }\n" +
    "    ],\n" +
    "    \"rn\": 5113372\n" +
    "  }";
  private static final String reportJson2 = "{\n" +
    "    \"activities\": [\n" +
    "    {\n" +
    "      \"rn\": 5113371,\n" +
    "      \"acronym\": \"HVSD\",\n" +
    "      \"status\": 2\n" +
    "    },\n" +
    "    {\n" +
    "      \"rn\": 5113371,\n" +
    "      \"acronym\": \"PSEC\",\n" +
    "      \"status\": 2\n" +
    "    }\n" +
    "    ],\n" +
    "    \"rn\": 5113371\n" +
    "  }";


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
  private FlagReportDao flagReportDao;

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

    SURVEY_ACRONYM_LIST = new ArrayList<>();
    SURVEY_ACRONYM_LIST.add("HVSD");
    SURVEY_ACRONYM_LIST.add("PSEC");
    SURVEY_ACRONYM_LIST.add("ABC");
    SURVEY_ACRONYM_LIST.add("DEF");
    when(surveyDao.listAcronyms()).thenReturn(SURVEY_ACRONYM_LIST);

    PROGRESS_REPORT_LIST.add(ActivitiesProgressReport.deserialize(reportJson1));
    PROGRESS_REPORT_LIST.add(ActivitiesProgressReport.deserialize(reportJson2));
    when(flagReportDao.getActivitiesProgressReport("BA")).thenReturn(PROGRESS_REPORT_LIST);
    when(flagReportDao.getActivitiesProgressReport()).thenReturn(PROGRESS_REPORT_LIST);

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


  //TODO 28/11/18: uncomment
//  @Test
//  public void method_get_activities_progress_should_padronize_the_result_array_with_the_survey_list() {
//    ArrayList<ActivitiesProgressReport> reportList = monitoringServiceBean.getActivitiesProgress("BA");
//
//    assertEquals(reportList.get(0).getActivities().size(), SURVEY_ACRONYM_LIST.size());
//    assertEquals(reportList.get(1).getActivities().size(), SURVEY_ACRONYM_LIST.size());
//    assertEquals(reportList.get(0).getActivities().get(0).getAcronym(), reportList.get(1).getActivities().get(0).getAcronym());
//    assertEquals(reportList.get(0).getActivities().get(1).getAcronym(), reportList.get(1).getActivities().get(1).getAcronym());
//    assertEquals(reportList.get(0).getActivities().get(2).getAcronym(), reportList.get(1).getActivities().get(2).getAcronym());
//    assertEquals(reportList.get(0).getActivities().get(3).getAcronym(), reportList.get(1).getActivities().get(3).getAcronym());
//
//
//    reportList = monitoringServiceBean.getActivitiesProgress();
//    assertEquals(reportList.get(0).getActivities().size(), SURVEY_ACRONYM_LIST.size());
//    assertEquals(reportList.get(1).getActivities().size(), SURVEY_ACRONYM_LIST.size());
//
//    assertEquals(reportList.get(0).getActivities().get(0).getAcronym(), reportList.get(1).getActivities().get(0).getAcronym());
//    assertEquals(reportList.get(0).getActivities().get(1).getAcronym(), reportList.get(1).getActivities().get(1).getAcronym());
//    assertEquals(reportList.get(0).getActivities().get(2).getAcronym(), reportList.get(1).getActivities().get(2).getAcronym());
//    assertEquals(reportList.get(0).getActivities().get(3).getAcronym(), reportList.get(1).getActivities().get(3).getAcronym());
//  }

}
