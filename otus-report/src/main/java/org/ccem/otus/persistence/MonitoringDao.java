package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;

import java.util.ArrayList;
import java.util.List;

public interface MonitoringDao {

    List<MonitoringDataSourceResult> getAll() throws ValidationException;

    ArrayList<MonitoringDataSourceResult> get(String acronym) throws ValidationException;
}
