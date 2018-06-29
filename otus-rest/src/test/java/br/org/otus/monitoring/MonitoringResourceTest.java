package br.org.otus.monitoring;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.report.ReportResource;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class MonitoringResourceTest {
  
  private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String AUTHORIZATION_HEADER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final ArrayList<String> LIST_ACRONYMS_CENTERS = new ArrayList<>();
  private static final MonitoringCenter MONITORING_CENTERS = new MonitoringCenter();
  private static final String responseMonitoringCenters = "{\"data\":[{\"name\":\"Bahia\",\"goal\":3025,\"backgroundColor\":\"rgba(255, 99, 132, 0.2)\",\"borderColor\":\"rgba(255, 99, 132, 1)\"}]}";
  private static final String responseListAcronyms = "{\"data\":[\"ACTA\",\"MONC\",\"CISE\"]}";
  private static final Long GOAL = (long) 3025L;
  @InjectMocks
  private MonitoringResource monitoringResource;
  
  @Mock
  private MonitoringFacade monitoringFacade;
  
  
  private ArrayList<MonitoringCenter> monitoringCenters = new ArrayList<MonitoringCenter>();
  private ArrayList<String> acronymsList = new ArrayList<>();
  
  
  @Before
  public void setUp() throws DataNotFoundException {
    
    MONITORING_CENTERS.setGoal(GOAL);;
    MONITORING_CENTERS.setName("Bahia");
    MONITORING_CENTERS.setBackgroundColor("rgba(255, 99, 132, 0.2)");
    MONITORING_CENTERS.setBorderColor("rgba(255, 99, 132, 1)");
    
    acronymsList.add("ACTA");
    acronymsList.add("MONC");
    acronymsList.add("CISE");
    
    monitoringCenters.add(MONITORING_CENTERS);
    when(monitoringFacade.getMonitoringCenters()).thenReturn(monitoringCenters);
    when(monitoringFacade.listActivities()).thenReturn(acronymsList);
    
  }
  
  @Test
  public void method_getMonitoring_should_return_MonitoringCenter_list() {
    assertEquals(responseMonitoringCenters, monitoringResource.getMonitoring());
  }
  
  @Test
  public void method_listActivities_should_return_Acronyms_Activities_list() {
    System.out.println(monitoringResource.listActivities());
    assertEquals(responseListAcronyms, monitoringResource.listActivities());
  }

}
