package br.org.otus.report;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.model.ActivityReportTemplate;
import org.ccem.otus.model.ReportTemplate;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
import br.org.otus.security.context.SecurityContext;
import org.jboss.resteasy.annotations.Query;

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
  @Path("/participant/list/app/{recruitmentNumber}")
  public String listByParticipantPaginated(@PathParam("recruitmentNumber") Long recruitmentNumber, @QueryParam("page") int page) {
    return new Response().buildSuccess(reportFacade.getReportByParticipantPaginated(recruitmentNumber, page)).toSurveyJson();
  }

  @GET
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/participant/{recruitmentNumber}/{reportId}")
  public String getParticipantReport(@PathParam("recruitmentNumber") Long recruitmentNumber, @PathParam("reportId") String reportId) {
    return new Response().buildSuccess(reportFacade.getParticipantReport(recruitmentNumber, reportId)).toJson(ReportTemplate.getResponseGsonBuilder());
  }

  @GET
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/activity-report/{activityId}")
  public String getActivityReport(@PathParam("activityId") String activityId) {
    return new Response().buildSuccess(reportFacade.getActivityReport(activityId)).toJson(ActivityReportTemplate.getResponseGsonBuilder());
  }

  @POST
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/activity-report")
  public String createActivityReport(@Context HttpServletRequest request, String activityReportTemplateJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    return new Response().buildSuccess(reportFacade.createActivityReport(activityReportTemplateJson, userEmail)).toJson(ActivityReportTemplate.getGsonBuilder());
  }

  @GET
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/activity-report/list/{acronym}")
  public String getActivityReportList(@PathParam("acronym") String acronym) {
    return new Response().buildSuccess(reportFacade.getActivityReportList(acronym)).toJson(ActivityReportTemplate.getResponseGsonBuilder());
  }

  @PUT
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/activity-report/update/{reportId}")
  public String updateActivityReport(@PathParam("reportId") String reportId, String activityReportJson) {
    reportFacade.updateActivityReport(reportId, activityReportJson);
    return new Response().buildSuccess().toJson();
  }

}
