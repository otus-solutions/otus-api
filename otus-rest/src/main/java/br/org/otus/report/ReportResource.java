package br.org.otus.report;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getByRecruitmentNumber(String parameters) throws DataNotFoundException, NullPointerException {
        RequestParameters requestParameters = RequestParameters.deserialize(parameters);
        return new Response().buildSuccess(ReportTemplate.serialize(reportFacade.getByReportId(requestParameters))).toJson();
    }
}
