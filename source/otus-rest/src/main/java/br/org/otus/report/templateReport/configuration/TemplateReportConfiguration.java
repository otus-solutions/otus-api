package br.org.otus.report.templateReport.configuration;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;
import br.org.otus.report.templateReport.TemplateReportFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/template")
public class TemplateReportConfiguration {

  @Inject
  private TemplateReportFacade templateReportFacade;

  @GET
  @Path("/{recruitmentNumber}/{reportId}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String getReportTemplate(@PathParam("recruitmentNumber") Long recruitmentNumber, @PathParam("reportId") String reportId)  throws DataNotFoundException, ValidationException {
    return new Response().buildSuccess(templateReportFacade.getReportTemplate(recruitmentNumber, reportId)).toJson();
  }

}
