package br.org.otus.report;

import br.org.otus.rest.Response;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.RequestParameters;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/report")
public class ReportResource {

    @Inject
    private ReportFacade reportFacade;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getByRecruitmentNumber(String parameters) {
        RequestParameters requestParameters = RequestParameters.deserialize(parameters);
        return new Response().buildSuccess(reportFacade.getByReportById(requestParameters)).toJson();
    }
}
