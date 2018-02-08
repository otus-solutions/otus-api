package br.org.otus.report;

import java.util.ArrayList;

import javax.inject.Inject;

import org.ccem.otus.model.DataSourceModel;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.service.ReportService;

public class ReportFacade {

    @Inject
    private ReportService reportService;

    public ReportTemplate getByReportId(long ri) {
        try {
            return reportService.find(ri);
        } catch (Exception e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

}
