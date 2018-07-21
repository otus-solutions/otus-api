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
    public void enableParticipantRegistration(boolean permission) throws DataNotFoundException {
        projectConfigurationDao.enableParticipantRegistration(permission);
    }

    @Override
    public ProjectConfiguration getProjectConfiguration() throws DataNotFoundException {
        return projectConfigurationDao.getProjectConfiguration();
    }
}
