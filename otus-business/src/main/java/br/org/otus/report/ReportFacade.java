package br.org.otus.report;

import java.util.List;

import javax.inject.Inject;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.service.ReportService;

public class ReportFacade {

    @Inject
    private ReportService reportService;
    
    public ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId){
        try {
            return reportService.getParticipantReport(recruitmentNumber, reportId);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }
    
    public List<ReportTemplate> getReportByParticipant(Long recruitmentNumber){
        try {
            return reportService.getReportByParticipant(recruitmentNumber);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }
    
    public ReportTemplate create(String reportUploadJson, String userEmail){
    	ReportTemplate insertedReport;
    	try {
    		ReportTemplate reportTemplate = ReportTemplate.deserialize(reportUploadJson);
            reportTemplate.setSender(userEmail);
            insertedReport = reportService.create(reportTemplate);
    	}catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        } catch (ValidationException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        } catch (Exception e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    	
    	
    	return insertedReport;
    	
    }
    
    public void deleteById(String id){
        try {
        	reportService.delete(id);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        }
    }
    
    public List<ReportTemplate> list(){
        return reportService.list();
    }
    
    public ReportTemplate getById(String id){
        try {
            return reportService.getByID(id);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        }
    }
    
    public ReportTemplate update(ReportTemplate reportTemplate) {
    	try {
            return reportService.update(reportTemplate);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        } catch (ValidationException e) {
            throw new HttpResponseException(
                    ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        }
    }

}
