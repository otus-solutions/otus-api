package br.org.otus.project.configuration.service;

import br.org.otus.project.configuration.model.ProjectConfiguration;

public interface ProjectConfigurationService {

    void enableNewParticipants(boolean allowance);

    ProjectConfiguration getProjectConfiguration();

}
