package br.org.otus.project.configuration.persistence;

import br.org.otus.project.configuration.model.ProjectConfiguration;

public interface ProjectConfigurationDao {

    void enableNewParticipants(boolean permission);

    ProjectConfiguration getProjectConfiguration();
}
