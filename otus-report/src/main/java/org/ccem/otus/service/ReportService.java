package org.ccem.otus.service;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;

public interface ReportService {

    ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException;
    
    String create(ReportTemplate reportTemplate, String userEmail) throws DataNotFoundException, ValidationException;

}
