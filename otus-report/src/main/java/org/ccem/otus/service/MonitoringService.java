package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.*;

import java.util.ArrayList;
import java.util.List;

public interface MonitoringService {

    List<MonitoringDataSourceResult> get(String acronym) throws ValidationException;

    List<MonitoringCenter> getMonitoringCenter() throws DataNotFoundException;

    ArrayList<ActivitiesProgressReport> getActivitiesProgress();

    ArrayList<ActivitiesProgressReport> getActivitiesProgress(String center);

    ParticipantActivityReportDto getParticipantActivities(Long rn);

    ParticipantActivityRelationship setActivityApplicability(ActivityApplicability applicability);
}
