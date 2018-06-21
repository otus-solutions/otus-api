package org.ccem.otus.persistence;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.model.monitoring.MonitoringCenter;

import java.util.ArrayList;
import java.util.List;

public interface MonitoringDao {

    List<MonitoringDataSourceResult> getAll() throws ValidationException;

    ArrayList<MonitoringDataSourceResult> get(ArrayList<Document> query) throws ValidationException;

    MonitoringCenter getMonitoringCenter(FieldCenter center, Long goals) throws DataNotFoundException;
}
