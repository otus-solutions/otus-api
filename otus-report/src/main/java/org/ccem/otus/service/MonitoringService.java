package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;

import java.util.List;

public interface MonitoringService {

    List<MonitoringDataSourceResult> list() throws ValidationException;

    MonitoringDataSourceResult get(String acronym) throws ValidationException;
}
