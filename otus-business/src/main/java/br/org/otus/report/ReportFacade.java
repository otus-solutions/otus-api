package br.org.otus.report;

import javax.inject.Inject;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.RequestParameters;
import org.ccem.otus.service.ReportService;

public class ReportFacade {

    @Inject
    private ReportService reportService;

    public ReportTemplate getByReportId(RequestParameters requestParameters) throws DataNotFoundException, NullPointerException{
        try {
            return reportService.findReportById(requestParameters);
        } catch (DataNotFoundException | NullPointerException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

}
