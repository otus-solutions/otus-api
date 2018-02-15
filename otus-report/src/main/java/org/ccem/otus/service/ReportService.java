package org.ccem.otus.service;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;


public interface ReportService {
    ReportTemplate findReport(long ri, long rn) throws DataNotFoundException;
}
