package org.ccem.otus.service;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportTemplateDTO;

public interface ReportService {

    ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException, ValidationException;

    ReportTemplate getActivityReport(String activityId) throws DataNotFoundException, ValidationException;

    List<ReportTemplateDTO> getReportByParticipant(Long recruitmentNumber) throws DataNotFoundException, ValidationException;

    ReportTemplate create(ReportTemplate reportTemplate);
    
    void delete(String id) throws DataNotFoundException;
    
    List<ReportTemplate> list() throws ValidationException;
    
    ReportTemplate getByID(String id) throws DataNotFoundException, ValidationException;
    
    ReportTemplate updateFieldCenters(ReportTemplate reportTemplate) throws DataNotFoundException, ValidationException;

}
