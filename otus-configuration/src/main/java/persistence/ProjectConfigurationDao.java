package persistence;

import model.ProjectConfiguration;

public interface ProjectConfigurationDao {

    void enableNewParticipants(boolean permission);

    ProjectConfiguration getProjectConfiguration();
}
