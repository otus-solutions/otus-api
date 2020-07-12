package br.org.otus.report;

import java.net.MalformedURLException;
import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ActivityReportTemplate;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportTemplateDTO;
import org.ccem.otus.service.ReportService;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;

public class ReportFacade {

  @Inject
  private ReportService reportService;

  public ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) {
    try {
      return reportService.getParticipantReport(recruitmentNumber, reportId);
    } catch (DataNotFoundException | ValidationException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public ActivityReportTemplate getActivityReport(String activityId) {
    try {
      return reportService.getActivityReport(activityId);
    } catch (DataNotFoundException | MalformedURLException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));

    } catch (ValidationException e) {
      throw new HttpResponseException(
        Validation.build(e.getCause().getMessage(), e.getData()));
    }
  }

  public List<ReportTemplateDTO> getReportByParticipant(Long recruitmentNumber) {
    try {
      return reportService.getReportByParticipant(recruitmentNumber);
    } catch (DataNotFoundException | ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }
  public List<ReportTemplateDTO> getReportByParticipantPaginated(Long recruitmentNumber, int page) {
    try {
      return reportService.getReportByParticipantPaginated(recruitmentNumber, page);
    } catch (DataNotFoundException | ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public ReportTemplate create(String reportUploadJson, String userEmail) {
    ReportTemplate insertedReport;
    try {
      ReportTemplate reportTemplate = ReportTemplate.deserialize(reportUploadJson);
      reportTemplate.setSender(userEmail);
      insertedReport = reportService.create(reportTemplate);
      return insertedReport;
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public void deleteById(String id) {
    try {
      reportService.delete(id);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public List<ReportTemplate> list() {
    try {
      return reportService.list();
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public ReportTemplate getById(String id) {
    try {
      return reportService.getByID(id);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage(), e.getData()));
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public ReportTemplate updateFieldCenters(String updateReportJson) {
    try {
      ReportTemplate reportTemplate = ReportTemplate.deserialize(updateReportJson);
      return reportService.updateFieldCenters(reportTemplate);
    } catch (DataNotFoundException | ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public ActivityReportTemplate createActivityReport(String activityReportTemplateJson, String userEmail) {
    ActivityReportTemplate insertedReport;
    try {
      ActivityReportTemplate activityReportTemplate = ActivityReportTemplate.deserialize(activityReportTemplateJson);
      activityReportTemplate.setSender(userEmail);
      insertedReport = reportService.createActivityReport(activityReportTemplate);
      return insertedReport;
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public List<ActivityReportTemplate> getActivityReportList(String acronym) {
    try {
      return reportService.getActivityReportList(acronym);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }

  public void updateActivityReport(String reportId, String updateReportJson) {
    try {
      reportService.updateActivityReport(reportId, updateReportJson);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }

}
