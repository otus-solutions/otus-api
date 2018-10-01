package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.ActivitiesProgressionReport;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;

import java.util.ArrayList;
import java.util.List;

public interface MonitoringService {

    List<MonitoringDataSourceResult> get(String acronym) throws ValidationException;

    List<MonitoringCenter> getMonitoringCenter() throws DataNotFoundException;

    ArrayList<ActivitiesProgressionReport> getActivitiesProgress();

    ArrayList<ActivitiesProgressionReport> getActivitiesProgress(String center);
}
