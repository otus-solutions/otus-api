package br.org.otus.project.configuration.api;

import model.ProjectConfiguration;
import service.ProjectConfigurationService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

import javax.inject.Inject;

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

    public void enableNewParticipants(boolean permission) {
        try {
            projectConfigurationService.enableNewParticipants(permission);
        } catch (Exception e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }
}
