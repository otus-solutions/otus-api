package service;

import model.ProjectConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import persistence.ProjectConfigurationDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ProjectConfigurationServiceBean implements ProjectConfigurationService {

    @Inject
    ProjectConfigurationDao projectConfigurationDao;

    @Override
    public void enableNewParticipants(boolean allowance) throws DataNotFoundException {
        try {
            projectConfigurationDao.enableParticipantRegistration(allowance);
        } catch (DataNotFoundException e) {
            throw e; //TODO 19/07/18: test throws
        }

    }

    @Override
    public ProjectConfiguration getProjectConfiguration() throws DataNotFoundException {
        return projectConfigurationDao.getProjectConfiguration();
    }
}
