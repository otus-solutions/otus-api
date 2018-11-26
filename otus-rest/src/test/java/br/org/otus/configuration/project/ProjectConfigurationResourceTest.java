package br.org.otus.configuration.project;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.project.configuration.api.ProjectConfigurationFacade;
import model.ProjectConfiguration;

@RunWith(PowerMockRunner.class)
public class ProjectConfigurationResourceTest {
  private static final CharSequence POSITIVE_ANSWER = "true";
  private static String CONFIGURATION_JSON = "{\"objectType\": \"Project Configuration\", \"participantRegistration\": false\"}";
  @InjectMocks
  private ProjectConfigurationResource projectConfigurationResource;
  @Mock
  private ProjectConfigurationFacade projectConfigurationFacade;
  private ProjectConfiguration result;
  private String permissionResponse;

  @Test
  public void getProjectConfigurationMethod_should_return_configurationResponse() {
    result = ProjectConfiguration.deserialize(CONFIGURATION_JSON);
    PowerMockito.when(projectConfigurationFacade.getProjectConfiguration()).thenReturn(result);
   assertTrue(projectConfigurationResource.getProjectConfiguration().contains("Project Configuration"));
  }

  @Test
  public void allowNewParticipantsMethod_should_return_permissionResponse() {
    permissionResponse = projectConfigurationResource.allowNewParticipants(Boolean.TRUE);
    assertTrue(permissionResponse.contains(POSITIVE_ANSWER));
  }

  @Test
  public void autoGenerateRecruitmentNumber_should_return_permissionResponse() {
    permissionResponse = projectConfigurationResource.autoGenerateRecruitmentNumber(Boolean.TRUE);
    assertTrue(permissionResponse.contains(POSITIVE_ANSWER));
  }
}
