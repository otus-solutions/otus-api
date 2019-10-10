package org.ccem.otus.service;

import java.net.MalformedURLException;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ActivityReportTemplate;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportTemplateDTO;

public interface ReportService {

  ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException, ValidationException, MalformedURLException;

  ActivityReportTemplate getActivityReport(String activityId) throws DataNotFoundException, ValidationException, MalformedURLException;

  List<ReportTemplateDTO> getReportByParticipant(Long recruitmentNumber) throws DataNotFoundException, ValidationException;

  ReportTemplate create(ReportTemplate reportTemplate);

  void delete(String id) throws DataNotFoundException;

  List<ReportTemplate> list() throws ValidationException;

  ReportTemplate getByID(String id) throws DataNotFoundException, ValidationException;

  ReportTemplate updateFieldCenters(ReportTemplate reportTemplate) throws DataNotFoundException, ValidationException;

}
