package service;

import static org.powermock.api.mockito.PowerMockito.spy;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import persistence.ProjectConfigurationDao;

@RunWith(PowerMockRunner.class)
public class ProjectConfigurationServiceBeanTest {
  private static final boolean ALLOWANCE = true;
  @InjectMocks
  private ProjectConfigurationServiceBean projectConfigurationServiceBean;
  @Mock
  private ProjectConfigurationDao projectConfigurationDao;

  @Test
  public void enableNewParticipantsMethod_should() throws DataNotFoundException {
    projectConfigurationServiceBean.enableNewParticipants(ALLOWANCE);
    Mockito.verify(projectConfigurationDao, Mockito.times(1)).enableParticipantRegistration(ALLOWANCE);
  }

  @Test(expected = DataNotFoundException.class)
  public void getProjectConfigurationMethod_should_capture_DataNotFoundException() throws DataNotFoundException {
    DataNotFoundException e = spy(new DataNotFoundException());
    PowerMockito.doThrow(e).when(projectConfigurationDao).enableParticipantRegistration(ALLOWANCE);
    projectConfigurationServiceBean.enableNewParticipants(ALLOWANCE);
  }

  @Test
  public void getProjectConfigurationMethod_should() throws DataNotFoundException {
    projectConfigurationServiceBean.getProjectConfiguration();
    Mockito.verify(projectConfigurationDao, Mockito.times(1)).getProjectConfiguration();
  }

}
