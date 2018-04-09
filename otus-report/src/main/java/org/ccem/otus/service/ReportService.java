package org.ccem.otus.service;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;

public interface ReportService {

    ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException, ValidationException;
    
    List<ReportTemplate> getReportByParticipant(Long recruitmentNumber) throws DataNotFoundException, ValidationException;

    ReportTemplate create(ReportTemplate reportTemplate);
    
    void delete(String id) throws DataNotFoundException;
    
    List<ReportTemplate> list() throws ValidationException;
    
    ReportTemplate getByID(String id) throws DataNotFoundException, ValidationException;
    
    ReportTemplate update(ReportTemplate reportTemplate) throws DataNotFoundException, ValidationException;

}
