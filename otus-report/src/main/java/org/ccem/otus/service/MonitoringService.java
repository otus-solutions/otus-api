package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.ActivityProgressReportDto;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.model.monitoring.ParticipantActivityReportDto;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;

import java.util.ArrayList;
import java.util.List;

public interface MonitoringService {

    List<MonitoringDataSourceResult> get(String acronym) throws ValidationException;

    List<MonitoringCenter> getMonitoringCenter() throws DataNotFoundException;

    ActivityProgressReportDto getActivitiesProgress();

    ActivityProgressReportDto getActivitiesProgress(String center);

    ArrayList<ParticipantActivityReportDto> getParticipantActivities(Long rn);

    void setActivityApplicability(ActivityInapplicability applicability) throws DataNotFoundException;

    void deleteActivityApplicability(Long rn, String acronym) throws DataNotFoundException;
}
