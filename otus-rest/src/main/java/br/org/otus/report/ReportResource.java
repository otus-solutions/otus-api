package br.org.otus.report;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/report")
public class ReportResource {

    @Inject
    private ReportFacade reportFacade;

    @GET
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/participant/{recruitmentNumber}/{reportId}")
    public String getByRecruitmentNumber(@PathParam("recruitmentNumber") Long recruitmentNumber,@PathParam("reportId") String reportId){
        return new Response().buildSuccess(ReportTemplate.serialize(reportFacade.getParticipantReport(recruitmentNumber,reportId))).toJson();
    }
}
