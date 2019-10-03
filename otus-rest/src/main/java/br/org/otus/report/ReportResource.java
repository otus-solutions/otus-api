package br.org.otus.report;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;

import org.ccem.otus.model.ReportTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

	@POST
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String create(@Context HttpServletRequest request, String reportTemplateJson) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
		return new Response().buildSuccess(reportFacade.create(reportTemplateJson, userEmail)).toJson(ReportTemplate.getGsonBuilder());
	}

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String list() {
		return new Response().buildSuccess(reportFacade.list()).toJson(ReportTemplate.getGsonBuilder());
	}

	@GET
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getById(@PathParam("id") String id) {
		return new Response().buildSuccess(reportFacade.getById(id)).toJson(ReportTemplate.getGsonBuilder());
	}

	@PUT
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String updateFieldCenters(String reportTemplateJson) {
		ReportTemplate updatedReport = reportFacade.updateFieldCenters(reportTemplateJson);
		return new Response().buildSuccess(updatedReport).toJson(ReportTemplate.getGsonBuilder());
	}

	@DELETE
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(@PathParam("id") String id) {
		reportFacade.deleteById(id);
		return new Response().buildSuccess().toJson();
	}

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/participant/list/{recruitmentNumber}")
	public String listByParticipant(@PathParam("recruitmentNumber") Long recruitmentNumber) {
		return new Response().buildSuccess(reportFacade.getReportByParticipant(recruitmentNumber)).toSurveyJson();
	}

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/participant/{recruitmentNumber}/{reportId}")
	public String getParticipantReport(@PathParam("recruitmentNumber") Long recruitmentNumber, @PathParam("reportId") String reportId) {
		return new Response().buildSuccess(reportFacade.getParticipantReport(recruitmentNumber, reportId)).toJson(ReportTemplate.getResponseGsonBuilder());
	}

	@GET
//	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/activity-report/{activityId}")
	public String getActivityReport(@PathParam("activityId") String activityId) {
		//todo para testar
		return new Response().buildSuccess(reportFacade.getActivityReport(activityId)).toJson(ReportTemplate.getResponseGsonBuilder());
	}
}
