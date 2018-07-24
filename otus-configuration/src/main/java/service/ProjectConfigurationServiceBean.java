package service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import model.ProjectConfiguration;
import persistence.ProjectConfigurationDao;

@Stateless
public class ProjectConfigurationServiceBean implements ProjectConfigurationService {

  @Inject
  ProjectConfigurationDao projectConfigurationDao;

  @Override
  public void enableNewParticipants(boolean allowance) throws DataNotFoundException {
    try {
      projectConfigurationDao.enableParticipantRegistration(allowance);
    } catch (DataNotFoundException e) {
      throw e; // TODO 19/07/18: test throws
    }
  }

  @Override
  public ProjectConfiguration getProjectConfiguration() throws DataNotFoundException {
    return projectConfigurationDao.getProjectConfiguration();
  }
}
