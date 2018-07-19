package service;

import model.ProjectConfiguration;

public interface ProjectConfigurationService {

    void enableNewParticipants(boolean allowance);

    ProjectConfiguration getProjectConfiguration();

}
