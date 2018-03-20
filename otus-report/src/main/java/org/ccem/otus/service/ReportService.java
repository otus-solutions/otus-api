package org.ccem.otus.service;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;

public interface ReportService {

    ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException;
    
    List<ReportTemplate> getReportByParticipant(Long recruitmentNumber) throws DataNotFoundException;
    
    String create(ReportTemplate reportTemplate, String userEmail) throws DataNotFoundException, ValidationException;
    
    void delete(String id) throws DataNotFoundException;
    
    List<ReportTemplate> list();
    
    ReportTemplate getByID(String id) throws DataNotFoundException;
    
    ReportTemplate update(ReportTemplate reportTemplate) throws DataNotFoundException, ValidationException;

}
