package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.model.monitoring.MonitoringCenter;

import java.util.ArrayList;

public interface MonitoringService {

    ArrayList<MonitoringDataSourceResult> get(String acronym) throws ValidationException;

    ArrayList<MonitoringCenter> getMonitoringCenter() throws ValidationException, DataNotFoundException;
}
