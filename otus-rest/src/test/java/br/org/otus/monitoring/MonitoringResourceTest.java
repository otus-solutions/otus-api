package br.org.otus.monitoring;

import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
public class MonitoringResourceTest {

  private static final MonitoringCenter MONITORING_CENTERS = new MonitoringCenter();
  private static final String responseMonitoringCenters = "{\"data\":[{\"name\":\"Bahia\",\"goal\":3025,\"backgroundColor\":\"rgba(255, 99, 132, 0.2)\",\"borderColor\":\"rgba(255, 99, 132, 1)\"}]}";
  private static final String responseListAcronyms = "{\"data\":[\"ACTA\",\"MONC\",\"CISE\"]}";
  private static final String responseMonitoringDataSourceResults = "{\"data\":[{\"acronym\":\"ACTA\"}]}";
  private static final Long GOAL = (long) 3025L;
  private static final String ACRONYM = "ACTA";
  private static final String CENTER = "RS";
  private static final Long RN = Long.valueOf(7016098);

  @InjectMocks
  private MonitoringResource monitoringResource;
  @Mock
  private MonitoringFacade monitoringFacade;


  private MonitoringDataSourceResult monitoringDataSourceResult;
  private ArrayList<MonitoringCenter> monitoringCenters;
  private ArrayList<String> acronymsList;
  List<MonitoringDataSourceResult> monitoringDataSourceResults;

  @Before
  public void setUp() {

    monitoringCenters = new ArrayList();
    acronymsList = new ArrayList();
    monitoringDataSourceResults = new ArrayList();

    MONITORING_CENTERS.setGoal(GOAL);;
    MONITORING_CENTERS.setName("Bahia");
    MONITORING_CENTERS.setBackgroundColor("rgba(255, 99, 132, 0.2)");
    MONITORING_CENTERS.setBorderColor("rgba(255, 99, 132, 1)");
    
    acronymsList.add("ACTA");
    acronymsList.add("MONC");
    acronymsList.add("CISE");

    monitoringDataSourceResult = new MonitoringDataSourceResult();
    setInternalState(monitoringDataSourceResult, "acronym", ACRONYM);
    monitoringDataSourceResults.add(monitoringDataSourceResult);

    monitoringCenters.add(MONITORING_CENTERS);
    when(monitoringFacade.getMonitoringCenters()).thenReturn(monitoringCenters);
    when(monitoringFacade.listActivities()).thenReturn(acronymsList);
    when(monitoringFacade.get(ACRONYM)).thenReturn(monitoringDataSourceResults);
  }

  @Test
  public void method_getParticipantExams_should_call_examMonitoringDao_getParticipantExams() {
    monitoringResource.getParticipantExamsProgress(RN);
    verify(monitoringFacade,times(1)).getParticipantExamsProgress(RN);
  }

  @Test
  public void method_setExamInapplicability_should_call_examInapplicabilityDao_update() {
    monitoringResource.defineExamInapplicability(anyObject());
    verify(monitoringFacade,times(1)).setExamApplicability(anyObject());
  }

  @Test
  public void method_deleteExamInapplicability_should_call_examInapplicabilityDao_delete() {
    monitoringResource.deleteExamInapplicability(anyObject());
    verify(monitoringFacade,times(1)).deleteExamInapplicability(anyObject());
  }

  @Test
  public void method_listActivities_should_return_Acronyms_Activities_list() {
    monitoringResource.listActivities();
    assertEquals(responseListAcronyms, monitoringResource.listActivities());
  }
  
  @Test
  public void method_getMonitoring_should_return_MonitoringCenter_list() {
    assertEquals(responseMonitoringCenters, monitoringResource.getMonitoring());
  }

  @Test
  public void getMethod_should_returns_MonitoringDataSourceResult_by_Acronym() {
    assertEquals(responseMonitoringDataSourceResults, monitoringResource.get(ACRONYM));
  }

  @Test
  public void getProjectStatus_should_invoke_getActivitiesProgress_of_facade_by_Monitoring() {
    monitoringResource.getProjectStatus();
    verify(monitoringFacade, times(1)).getActivitiesProgress();
  }

  @Test
  public void getDataOrphanByExamMethod_should_invoke_getDataOrphanByExams_of_facade_by_Monitoring() {
    monitoringResource.getDataOrphanByExam();
    verify(monitoringFacade, times(1)).getDataOrphanByExams();
  }

  @Test
  public void getDataQuantitativeByTypeOfAliquotMethod_should_invoke_getDataQuantitativeByTypeOfAliquots_of_facade_by_Monitoring() {
    monitoringResource.getDataQuantitativeByTypeOfAliquot(CENTER);
    verify(monitoringFacade, times(1)).getDataQuantitativeByTypeOfAliquots(CENTER);
  }

  @Test
  public void getDataOfPendingResultsByAliquotMethod_should_invoke_getDataOfPendingResultsByAliquot_of_facade_by_Monitoring() {
    monitoringResource.getDataOfPendingResultsByAliquot(CENTER);
    verify(monitoringFacade, times(1)).getDataOfPendingResultsByAliquot(CENTER);
  }

  @Test
  public void getDataOfStorageByAliquotMethod_should_invoke_getDataOfStorageByAliquot_of_facade_by_Monitoring() {
    monitoringResource.getDataOfStorageByAliquot(CENTER);
    verify(monitoringFacade, times(1)).getDataOfStorageByAliquot(CENTER);
  }

  @Test
  public void getDataByExamMethod_should_invoke_getDataByExam_of_facade_by_Monitoring() {
    monitoringResource.getDataByExam(CENTER);
    verify(monitoringFacade, times(1)).getDataByExam(CENTER);
  }

    @Test
  public void getDataToCSVOfPendingResultsByAliquotsMethod_should_invoke_getDataToCSVOfPendingResultsByAliquots_of_facade_by_Monitoring() {
    monitoringResource.getDataToCSVOfPendingResultsByAliquots(CENTER);
    verify(monitoringFacade, times(1)).getDataToCSVOfPendingResultsByAliquots(CENTER);
  }


    @Test
  public void getDataToCSVOfOrphansByExamMethod_should_invoke_getDataToCSVOfOrphansByExam_of_facade_by_Monitoring() {
    monitoringResource.getDataToCSVOfOrphansByExam();
    verify(monitoringFacade, times(1)).getDataToCSVOfOrphansByExam();
  }
}
