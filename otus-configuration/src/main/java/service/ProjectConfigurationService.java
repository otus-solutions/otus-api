package service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import model.ProjectConfiguration;

public interface ProjectConfigurationService {

    void enableParticipantRegistration(boolean permission) throws DataNotFoundException;

  ProjectConfiguration getProjectConfiguration() throws DataNotFoundException;

}
