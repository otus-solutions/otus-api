package br.org.otus.report;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;

import org.ccem.otus.model.ReportTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/report")
public class ReportResource {

    @Inject
    private ReportFacade reportFacade;
    
    @Inject
    private SecurityContext securityContext;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/participant/{recruitmentNumber}/{reportId}")
    public String getByRecruitmentNumber(@PathParam("recruitmentNumber") Long recruitmentNumber,@PathParam("reportId") String reportId){
        return new Response().buildSuccess(ReportTemplate.serialize(reportFacade.getParticipantReport(recruitmentNumber,reportId))).toJson();
    }
    
    @POST
    @Secured
    @Produces (MediaType.APPLICATION_JSON)
    @Consumes (MediaType.APPLICATION_JSON)
    public String create(@Context HttpServletRequest request, String reportUploadJson){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
        return new Response().buildSuccess(reportFacade.create(reportUploadJson, userEmail)).toJson();
    }
}
