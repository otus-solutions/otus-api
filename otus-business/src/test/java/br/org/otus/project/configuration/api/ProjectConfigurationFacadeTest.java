package br.org.otus.project.configuration.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.HttpResponseException;
import service.ProjectConfigurationService;

@RunWith(PowerMockRunner.class)
public class ProjectConfigurationFacadeTest {
  private static final boolean PERMISSION = Boolean.TRUE;
  @InjectMocks
  private ProjectConfigurationFacade projectConfigurationFacade;
  @Mock
  private ProjectConfigurationService projectConfigurationService;

  @Test
  public void getProjectConfigurationMethod_should_invoke_getProjectConfiguration_of_projectConfigurationService()
      throws DataNotFoundException {
    projectConfigurationFacade.getProjectConfiguration();
    verify(projectConfigurationService, times(1)).getProjectConfiguration();
  }

  @Test(expected = HttpResponseException.class)
  public void getProjectConfigurationMethod_should_capture_DataNotFoundException() throws DataNotFoundException {
    DataNotFoundException e = spy(new DataNotFoundException());
    PowerMockito.doThrow(e).when(projectConfigurationService).getProjectConfiguration();
    projectConfigurationFacade.getProjectConfiguration();
  }

  @Test
  public void enableNewParticipantsMethod_should_invoke_enableNewParticipants_of_projectConfigurationService()
      throws DataNotFoundException {
    projectConfigurationFacade.enableParticipantRegistration(PERMISSION);
    verify(projectConfigurationService, times(1)).enableParticipantRegistration(PERMISSION);
  }

  @Test(expected = HttpResponseException.class)
  public void enableNewParticipantsMethod_should_capture_DataNotFoundException() throws DataNotFoundException {
    DataNotFoundException e = spy(new DataNotFoundException());
    PowerMockito.doThrow(e).when(projectConfigurationService).enableParticipantRegistration(PERMISSION);
    projectConfigurationFacade.enableParticipantRegistration(PERMISSION);
  }

  @Test
  public void autoGenerateRecruitmentNumber_should_invoke_autoGenerateRecruitmentNumber_of_projectConfigurationService()
          throws DataNotFoundException {
    projectConfigurationFacade.autoGenerateRecruitmentNumber(PERMISSION);
    verify(projectConfigurationService, times(1)).autoGenerateRecruitmentNumber(PERMISSION);
  }

  @Test(expected = HttpResponseException.class)
  public void autoGenerateRecruitmentNumber_should_capture_DataNotFoundException() throws DataNotFoundException {
    DataNotFoundException e = spy(new DataNotFoundException());
    PowerMockito.doThrow(e).when(projectConfigurationService).autoGenerateRecruitmentNumber(PERMISSION);
    projectConfigurationFacade.autoGenerateRecruitmentNumber(PERMISSION);
  }

}
