package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import java.util.List;

public interface MonitoringDao {

    List<MonitoringDataSourceResult> getAll() throws ValidationException;

}
