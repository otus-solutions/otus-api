package br.org.otus.report;

import javax.inject.Inject;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.service.ReportService;

public class ReportFacade {

    @Inject
    private ReportService reportService;

    public ReportTemplate getByReportId(long ri, long rn) {
        try {
            return reportService.findReport(ri,rn);
        } catch (Exception e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

}
