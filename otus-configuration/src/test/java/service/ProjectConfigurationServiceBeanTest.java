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
  private static final boolean PERMISSION = true;
  @InjectMocks
  private ProjectConfigurationServiceBean projectConfigurationServiceBean;
  @Mock
  private ProjectConfigurationDao projectConfigurationDao;

  @Test
  public void enableParticipantsMethod_should() throws DataNotFoundException {
    projectConfigurationServiceBean.enableParticipantRegistration(PERMISSION);
    Mockito.verify(projectConfigurationDao, Mockito.times(1)).enableParticipantRegistration(PERMISSION);
  }

  @Test
  public void getProjectConfigurationMethod_should() throws DataNotFoundException {
    projectConfigurationServiceBean.getProjectConfiguration();
    Mockito.verify(projectConfigurationDao, Mockito.times(1)).getProjectConfiguration();
  }

}
