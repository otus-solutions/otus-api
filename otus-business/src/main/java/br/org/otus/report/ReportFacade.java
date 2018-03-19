package br.org.otus.report;

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
    
    public String create(String reportUploadJson, String userEmail){
    	String reportUploadId;
    	try {
    		ReportTemplate reportTemplate = ReportTemplate.deserialize(reportUploadJson);
    		reportUploadId = reportService.create(reportTemplate, userEmail);
    	}catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        } catch (ValidationException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        } catch (Exception e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    	
    	
    	return reportUploadId;
    	
    }

}
