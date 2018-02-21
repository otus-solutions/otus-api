package org.ccem.otus.service;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.RequestParameters;


public interface ReportService {

    ReportTemplate findReportById(RequestParameters requestParameters) throws DataNotFoundException;

}
