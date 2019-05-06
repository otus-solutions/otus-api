package br.org.otus.monitoring;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.api.SurveyFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.ProgressReport;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.service.LaboratoryMonitoringService;
import org.ccem.otus.service.MonitoringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class MonitoringFacadeTest {
  private static final String ACRONYM = "DIEC";
  private static final String CENTER = "MG";
  private static final Long RN = Long.valueOf(7016098);
  private static final ValidationException VALIDATION_EXCEPTION = new ValidationException(new Throwable("Message"));
  private static final DataNotFoundException DATA_NOT_FOUND_EXCEPTION= new DataNotFoundException(new Throwable("Message"));

  @InjectMocks
  private MonitoringFacade monitoringFacade;

  @Mock
  private SurveyFacade surveyFacade;

  @Mock
  private MonitoringService monitoringService;

  @Mock
  private LaboratoryMonitoringService laboratoryMonitoringService;

  @Mock
  private LaboratoryConfigurationService laboratoryConfigurationService;

  @Mock
  private ParticipantFacade participantFacade;

  @Mock
  private LaboratoryProgressDTO laboratoryProgressDTO;

  @Mock
  private ProgressReport progressReport;

  @Test(expected = HttpResponseException.class)
  public void get_should_call_method_get() throws ValidationException {
    Mockito.when(monitoringService.get(ACRONYM)).thenThrow(VALIDATION_EXCEPTION);

    monitoringFacade.get(ACRONYM);
    Mockito.verify(monitoringService).get(ACRONYM);
  }

  @Test
  public void method_getParticipantExams_should_call_examMonitoringDao_getParticipantExams() throws DataNotFoundException{
    monitoringFacade.getParticipantExamsProgress(RN);
    Mockito.verify(monitoringService,times(1)).getParticipantExams(RN);
  }

  @Test
  public void method_setExamInapplicability_should_call_examInapplicabilityDao_update() {
    monitoringFacade.setExamApplicability(anyObject());
    Mockito.verify(monitoringService,times(1)).setExamInapplicability(anyObject());
  }

  @Test
  public void method_deleteExamInapplicability_should_call_examInapplicabilityDao_delete() {
    monitoringFacade.deleteExamInapplicability(anyObject());
    Mockito.verify(monitoringService,times(1)).deleteExamInapplicability(anyObject());
  }

  @Test
  public void listActivities_should_call_method_listActivities() {
    monitoringFacade.listActivities();
    Mockito.verify(surveyFacade).listAcronyms();
  }

  @Test(expected = HttpResponseException.class)
  public void getMonitoringCenters_should_call_method_getMonitoringCenters() throws DataNotFoundException {
    Mockito.when(monitoringService.getMonitoringCenter()).thenThrow(DATA_NOT_FOUND_EXCEPTION);

    monitoringFacade.getMonitoringCenters();
    Mockito.verify(monitoringService).getMonitoringCenter();
  }

  @Test
  public void getActivitiesProgress_should_call_method_getActivitiesProgress() throws DataNotFoundException {
    monitoringFacade.getActivitiesProgress();
    Mockito.verify(monitoringService).getActivitiesProgress();
  }

  @Test
  public void getActivitiesProgress_should_call_method_getActivitiesProgress_with_center() throws DataNotFoundException {
    monitoringFacade.getActivitiesProgress(CENTER);
    Mockito.verify(monitoringService).getActivitiesProgress(CENTER);
  }

  @Test
  public void getDataOrphanByExams_should_call_monitoringService_getDataOrphanByExams() throws DataNotFoundException {
    monitoringFacade.getDataOrphanByExams();
    Mockito.verify(monitoringService).getDataOrphanByExams();
  }

  @Test
  public void getDataOrphanByExams_should_return_LaboratoryProgressDTO() throws DataNotFoundException {
    PowerMockito.when(monitoringService.getDataOrphanByExams()).thenReturn(laboratoryProgressDTO);
    assertEquals(laboratoryProgressDTO,monitoringFacade.getDataOrphanByExams());
  }

  @Test(expected = HttpResponseException.class)
  public void getDataOrphanByExams_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(monitoringService.getDataOrphanByExams()).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getDataOrphanByExams();
  }

  @Test
  public void getDataQuantitativeByTypeOfAliquots_should_call_monitoringService_getDataQuantitativeByTypeOfAliquots_with_center() throws DataNotFoundException {
    monitoringFacade.getDataQuantitativeByTypeOfAliquots(CENTER);
    Mockito.verify(monitoringService).getDataQuantitativeByTypeOfAliquots(CENTER);
  }

  @Test
  public void getDataQuantitativeByTypeOfAliquots_should_return_LaboratoryProgressDTO() throws DataNotFoundException {
    PowerMockito.when(monitoringService.getDataQuantitativeByTypeOfAliquots(CENTER)).thenReturn(laboratoryProgressDTO);
    assertEquals(laboratoryProgressDTO,monitoringFacade.getDataQuantitativeByTypeOfAliquots(CENTER));
  }

  @Test(expected = HttpResponseException.class)
  public void getDataQuantitativeByTypeOfAliquots_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(monitoringService.getDataQuantitativeByTypeOfAliquots(CENTER)).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getDataQuantitativeByTypeOfAliquots(CENTER);
  }

  @Test
  public void getDataOfPendingResultsByAliquot_should_call_monitoringService_getDataOfPendingResultsByAliquot_with_center() throws DataNotFoundException {
    monitoringFacade.getDataOfPendingResultsByAliquot(CENTER);
    Mockito.verify(monitoringService).getDataOfPendingResultsByAliquot(CENTER);
  }

  @Test
  public void getDataOfPendingResultsByAliquot_should_return_LaboratoryProgressDTO() throws DataNotFoundException {
    PowerMockito.when(monitoringService.getDataOfPendingResultsByAliquot(CENTER)).thenReturn(laboratoryProgressDTO);
    assertEquals(laboratoryProgressDTO,monitoringFacade.getDataOfPendingResultsByAliquot(CENTER));
  }

  @Test(expected = HttpResponseException.class)
  public void getDataOfPendingResultsByAliquot_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(monitoringService.getDataOfPendingResultsByAliquot(CENTER)).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getDataOfPendingResultsByAliquot(CENTER);
  }

  @Test
  public void getDataOfStorageByAliquot_should_call_monitoringService_getDataOfStorageByAliquot_with_center() throws DataNotFoundException {
    monitoringFacade.getDataOfStorageByAliquot(CENTER);
    Mockito.verify(monitoringService).getDataOfStorageByAliquot(CENTER);
  }

  @Test
  public void getDataOfStorageByAliquot_should_return_LaboratoryProgressDTO() throws DataNotFoundException {
    PowerMockito.when(monitoringService.getDataOfStorageByAliquot(CENTER)).thenReturn(laboratoryProgressDTO);
    assertEquals(laboratoryProgressDTO,monitoringFacade.getDataOfStorageByAliquot(CENTER));
  }

  @Test(expected = HttpResponseException.class)
  public void getDataOfStorageByAliquot_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(monitoringService.getDataOfStorageByAliquot(CENTER)).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getDataOfStorageByAliquot(CENTER);
  }

  @Test
  public void getDataByExam_should_call_monitoringService_getDataByExam_with_center() throws DataNotFoundException {
    monitoringFacade.getDataByExam(CENTER);
    Mockito.verify(monitoringService).getDataByExam(CENTER);
  }

  @Test
  public void getDataByExam_should_return_LaboratoryProgressDTO() throws DataNotFoundException {
    PowerMockito.when(monitoringService.getDataByExam(CENTER)).thenReturn(laboratoryProgressDTO);
    assertEquals(laboratoryProgressDTO,monitoringFacade.getDataByExam(CENTER));
  }

  @Test(expected = HttpResponseException.class)
  public void getDataByExam_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(monitoringService.getDataByExam(CENTER)).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getDataByExam(CENTER);
  }

  @Test
  public void getDataToCSVOfPendingResultsByAliquots_should_call_monitoringService_getDataToCSVOfPendingResultsByAliquots_with_center() throws DataNotFoundException {
    monitoringFacade.getDataToCSVOfPendingResultsByAliquots(CENTER);
    Mockito.verify(monitoringService).getDataToCSVOfPendingResultsByAliquots(CENTER);
  }

  @Test
  public void getDataToCSVOfPendingResultsByAliquots_should_return_LaboratoryProgressDTO() throws DataNotFoundException {
    PowerMockito.when(monitoringService.getDataToCSVOfPendingResultsByAliquots(CENTER)).thenReturn(laboratoryProgressDTO);
    assertEquals(laboratoryProgressDTO,monitoringFacade.getDataToCSVOfPendingResultsByAliquots(CENTER));
  }

  @Test(expected = HttpResponseException.class)
  public void getDataToCSVOfPendingResultsByAliquots_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(monitoringService.getDataToCSVOfPendingResultsByAliquots(CENTER)).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getDataToCSVOfPendingResultsByAliquots(CENTER);
  }

  @Test
  public void getDataToCSVOfOrphansByExam_should_call_monitoringService_getDataToCSVOfOrphansByExam() throws DataNotFoundException {
    monitoringFacade.getDataToCSVOfOrphansByExam();
    Mockito.verify(monitoringService).getDataToCSVOfOrphansByExam();
  }

  @Test
  public void getDataToCSVOfOrphansByExam_should_return_LaboratoryProgressDTO() throws DataNotFoundException {
    PowerMockito.when(monitoringService.getDataToCSVOfOrphansByExam()).thenReturn(laboratoryProgressDTO);
    assertEquals(laboratoryProgressDTO,monitoringFacade.getDataToCSVOfOrphansByExam());
  }

  @Test(expected = HttpResponseException.class)
  public void getDataToCSVOfOrphansByExam_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(monitoringService.getDataToCSVOfOrphansByExam()).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getDataToCSVOfOrphansByExam();
  }

  @Test
  public void getExamFlagReport_should_call_laboratoryMonitoringService_with_possibleExams_and_centerRecruitmentNumbers() throws DataNotFoundException {
    monitoringFacade.getExamFlagReport(CENTER);

    Mockito.when(laboratoryConfigurationService.listPossibleExams(CENTER)).thenReturn(Arrays.asList());
    Mockito.when(participantFacade.listCenterRecruitmentNumbers(CENTER)).thenReturn(new ArrayList<>());

    verify(laboratoryMonitoringService, times(1)).getExamFlagReport(new LinkedList<>(), new ArrayList<>());
  }


  @Test(expected = HttpResponseException.class)
  public void getExamFlagReport_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(laboratoryConfigurationService.listPossibleExams(CENTER)).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getExamFlagReport(CENTER);
  }

  @Test
  public void getExamFlagReportLabels_should_call_laboratoryConfigurationService_with_center() throws DataNotFoundException {
    monitoringFacade.getExamFlagReportLabels(CENTER);
    verify(laboratoryConfigurationService, times(1)).listPossibleExams(CENTER);
  }

  @Test(expected = HttpResponseException.class)
  public void getExamFlagReportLabels_should_throw_HttpResponseException() throws DataNotFoundException {
    Mockito.when(laboratoryConfigurationService.listPossibleExams(CENTER)).thenThrow(DATA_NOT_FOUND_EXCEPTION);
    monitoringFacade.getExamFlagReportLabels(CENTER);
  }
}
