package service;

import model.ProjectConfiguration;

import javax.ejb.Stateless;

@Stateless
public class ProjectConfigurationServiceBean implements ProjectConfigurationService {
    @Override
    public void enableNewParticipants(boolean allowance) {

    }

    @Override
    public ProjectConfiguration getProjectConfiguration() {
        return null;
    }
}
