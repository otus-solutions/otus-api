package br.org.otus.project.configuration.api;

import javax.inject.Inject;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import model.ProjectConfiguration;
import service.ProjectConfigurationService;

public class ProjectConfigurationFacade {

  @Inject
  private ProjectConfigurationService projectConfigurationService;

  public ProjectConfiguration getProjectConfiguration() {
    try {
      return projectConfigurationService.getProjectConfiguration();
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void enableParticipantRegistration(boolean permission) {
    try {
      projectConfigurationService.enableParticipantRegistration(permission);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void autoGenerateRecruitmentNumber(boolean permission) {
    try {
      projectConfigurationService.autoGenerateRecruitmentNumber(permission);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void addressCensusRequired(boolean permission) {
    try {
      projectConfigurationService.addressCensusRequired(permission);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }
}
