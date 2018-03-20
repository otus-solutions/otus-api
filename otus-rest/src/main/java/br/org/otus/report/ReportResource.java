package br.org.otus.report;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.model.ReportTemplate;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/report")
public class ReportResource {

    @Inject
    private ReportFacade reportFacade;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/participant/{recruitmentNumber}/{reportId}")
    public String getByRecruitmentNumber(@PathParam("recruitmentNumber") Long recruitmentNumber,@PathParam("reportId") String reportId){
        return new Response().buildSuccess(ReportTemplate.serialize(reportFacade.getParticipantReport(recruitmentNumber,reportId))).toJson();
    }
}
