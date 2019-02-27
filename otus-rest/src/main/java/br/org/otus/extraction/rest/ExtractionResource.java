package br.org.otus.extraction.rest;

import br.org.otus.extraction.ExtractionFacade;
import br.org.otus.extraction.SecuredExtraction;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.user.api.UserFacade;
import br.org.otus.user.dto.ManagementUserDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

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
  public byte[] extractActivities(@PathParam("acronym") String acronym, @PathParam("version") Integer version) throws DataNotFoundException {
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
  public byte[] extractExamsValues() throws DataNotFoundException {
    return extractionFacade.createLaboratoryExamsValuesExtraction();
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/laboratory")
  public byte[] extractLaboratory() throws DataNotFoundException {
    return extractionFacade.createLaboratoryExtraction();
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/activity/{acronym}/{version}/attachments")
  public byte[] extractAnnexesReport(@PathParam("acronym") String acronym, @PathParam("version") Integer version) throws DataNotFoundException {
    return extractionFacade.createAttachmentsReportExtraction(acronym.toUpperCase(), version);
  }

  @POST
  @Secured
  @Path("/enable")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String enableUsers(ManagementUserDto managementUserDto) {
    Response response = new Response();
    userFacade.enableExtraction(managementUserDto);
    return response.buildSuccess().toJson();
  }

  @POST
  @SecuredExtraction
  @Path("/activity/attachments")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public javax.ws.rs.core.Response fetch(ArrayList<String> oids) {
    javax.ws.rs.core.Response.ResponseBuilder builder = javax.ws.rs.core.Response.ok(extractionFacade.downloadFiles(oids));
    builder.header("Content-Disposition", "attachment; filename=" + "file-extraction.zip");
    javax.ws.rs.core.Response response = builder.build();
    return response;
  }

  @POST
  @Secured
  @Path("/disable")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String disableUsers(ManagementUserDto managementUserDto) {
    Response response = new Response();
    userFacade.disableExtraction(managementUserDto);
    return response.buildSuccess().toJson();
  }

  @POST
  @Secured
  @Path("/enable-ips")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String enableIps(ManagementUserDto managementUserDto) {
    Response response = new Response();
    userFacade.updateExtractionIps(managementUserDto);
    return response.buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("/extraction-token")
  @Produces(MediaType.APPLICATION_JSON)
  public String getToken(@Context HttpServletRequest request) {
    Response response = new Response();
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    String extractionToken = userFacade.getExtractionToken(userEmail);
    return response.buildSuccess(extractionToken).toJson();
  }

}
