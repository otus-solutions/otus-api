package br.org.otus.monitoring;

import br.org.otus.survey.api.SurveyFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.service.MonitoringService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class MonitoringFacadeTest {
  private static final String ACRONYM = "DIEC";
  private static final String CENTER = "MG";

  @InjectMocks
  private MonitoringFacade monitoringFacade;

  @Mock
  private SurveyFacade surveyFacade;

  @Mock
  private MonitoringService monitoringService;

  @Test
  public void get_should_call_method_get() throws ValidationException {
    monitoringFacade.get(ACRONYM);
    Mockito.verify(monitoringService).get(ACRONYM);
  }

  @Test
  public void listActivities_should_call_method_listActivities() {
    monitoringFacade.listActivities();
    Mockito.verify(surveyFacade).listAcronyms();
  }

  @Test
  public void getMonitoringCenters_should_call_method_getMonitoringCenters() throws DataNotFoundException {
    monitoringFacade.getMonitoringCenters();
    Mockito.verify(monitoringService).getMonitoringCenter();
  }

  @Test
  public void getActivitiesProgress_should_call_method_getActivitiesProgress() {
    monitoringFacade.getActivitiesProgress();
    Mockito.verify(monitoringService).getActivitiesProgress();
  }

  @Test
  public void getActivitiesProgress_should_call_method_getActivitiesProgress_with_center() {
    monitoringFacade.getActivitiesProgress(CENTER);
    Mockito.verify(monitoringService).getActivitiesProgress(CENTER);
  }
}