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
  public void enableParticipantRegistration(boolean permission) throws DataNotFoundException {
    projectConfigurationDao.enableParticipantRegistration(permission);
  }

  @Override
  public ProjectConfiguration getProjectConfiguration() throws DataNotFoundException {
    return projectConfigurationDao.getProjectConfiguration();
  }

  @Override
  public void autoGenerateRecruitmentNumber(boolean permission) throws DataNotFoundException{
    projectConfigurationDao.autoGenerateRecruitmentNumber(permission);
  }
}
