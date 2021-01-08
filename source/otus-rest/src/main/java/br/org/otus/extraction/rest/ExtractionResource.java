package br.org.otus.extraction.rest;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.extraction.ExtractionFacade;
import br.org.otus.extraction.SecuredExtraction;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.user.api.UserFacade;
import br.org.otus.user.dto.ManagementUserDto;
import com.google.gson.internal.LinkedTreeMap;

@Path("data-extraction")
public class ExtractionResource {

  @Inject
  private UserFacade userFacade;
  @Inject
  private ExtractionFacade extractionFacade;
  @Inject
  private SecurityContext securityContext;

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/activity/{acronym}/{version}")
  public byte[] extractActivities(@PathParam("acronym") String acronym, @PathParam("version") Integer version) {
    return extractionFacade.createActivityExtraction(acronym.toUpperCase(), version);
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/activity/{acronym}/versions")
  public String listSurveyVersions(@PathParam("acronym") String acronym) {
    return new Response().buildSuccess(extractionFacade.listSurveyVersions(acronym.toUpperCase())).toJson();
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/laboratory/exams-values")
  public byte[] extractExamsValues() {
    return extractionFacade.createLaboratoryExamsValuesExtraction();
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/laboratory")
  public byte[] extractLaboratory() {
    return extractionFacade.createLaboratoryExtraction();
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/activity/{acronym}/{version}/attachments")
  public byte[] extractAnnexesReport(@PathParam("acronym") String acronym, @PathParam("version") Integer version) {
    return extractionFacade.createAttachmentsReportExtraction(acronym.toUpperCase(), version);
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/activity/progress/{center}")
  public byte[] extractActivitiesProgress(@PathParam("center") String center) {
    return extractionFacade.createActivityProgressExtraction(center);
  }

  @POST
  @Secured
  @Path("/enable")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String enableUsers(ManagementUserDto managementUserDto) {
    userFacade.enableExtraction(managementUserDto);
    return new Response().buildSuccess().toJson();
  }

  @POST
  @Secured
  @Path("/disable")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String disableUsers(ManagementUserDto managementUserDto) {
    userFacade.disableExtraction(managementUserDto);
    return new Response().buildSuccess().toJson();
  }

  @POST
  @Secured
  @Path("/enable-ips")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String enableIps(ManagementUserDto managementUserDto) {
    userFacade.updateExtractionIps(managementUserDto);
    return new Response().buildSuccess().toJson();
  }

  @POST
  @SecuredExtraction
  @Path("/activity/attachments")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public javax.ws.rs.core.Response fetch(ArrayList<String> oids) {
    javax.ws.rs.core.Response.ResponseBuilder builder = javax.ws.rs.core.Response.ok(extractionFacade.downloadFiles(oids));
    builder.header("Content-Disposition", "attachment; filename=" + "file-extraction.zip");
    return builder.build();
  }

  @GET
  @Secured
  @Path("/extraction-token")
  @Produces(MediaType.APPLICATION_JSON)
  public String getToken(@Context HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    String extractionToken = userFacade.getExtractionToken(userEmail);
    return new Response().buildSuccess(extractionToken).toJson();
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/pipeline/json/{pipeline}")
  public String extractJsonFromPipeline(@PathParam("pipeline") String pipelineName) {
    ArrayList<LinkedTreeMap> json =  extractionFacade.createJsonExtractionFromPipeline(pipelineName);
    return new Response().buildSuccess(json).toJson();
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/pipeline/csv/{pipeline}")
  public byte[] extractCsvFromPipeline(@PathParam("pipeline") String pipelineName) {
    return extractionFacade.createCsvExtractionFromPipeline(pipelineName);
  }

  @PUT
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/activity/{id}")
  public String createActivityExtraction(@PathParam("id") String activityId) {
    extractionFacade.createActivityExtraction(activityId);
    return new Response().buildSuccess().toJson();
  }

  @PUT
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/activity/{id}")
  public String updateActivityExtraction(@PathParam("id") String activityId) {
    extractionFacade.updateActivityExtraction(activityId);
    return new Response().buildSuccess().toJson();
  }

  @DELETE
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/activity/{id}")
  public String deleteActivityExtraction(@PathParam("id") String activityId) {
    extractionFacade.deleteActivityExtraction(activityId);
    return new Response().buildSuccess().toJson();
  }

}
