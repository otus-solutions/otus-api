package org.ccem.otus.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;

public interface MonitoringDao {

    List<MonitoringDataSourceResult> getAll() throws ValidationException;

    ArrayList<MonitoringDataSourceResult> get(ArrayList<Document> query) throws ValidationException;

}
