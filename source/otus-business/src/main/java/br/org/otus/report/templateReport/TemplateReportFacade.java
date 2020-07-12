package br.org.otus.report.templateReport;

import br.org.otus.gateway.gates.ReportGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.exception.ResponseInfo;
import br.org.otus.response.info.Validation;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.service.ReportService;

import javax.inject.Inject;
import java.net.MalformedURLException;

public class TemplateReportFacade {

  @Inject
  private ReportService reportService;

  public Object getReportTemplate(Long recruitmentNumber, String reportId) throws DataNotFoundException, ValidationException {
    try {
      ReportTemplate report = reportService.getParticipantReport(recruitmentNumber, reportId);
      String reportJsonString = ReportTemplate.getResponseGsonBuilder().create().toJson(report);
      GatewayResponse gatewayResponse = new ReportGatewayService().getReportTemplate(reportJsonString);
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Object.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(javax.ws.rs.core.Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }
}
