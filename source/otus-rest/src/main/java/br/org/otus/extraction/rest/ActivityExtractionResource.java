package br.org.otus.extraction.rest;

import br.org.otus.extraction.ActivityExtractionFacade;
import br.org.otus.extraction.SecuredExtraction;
import br.org.otus.rest.Response;
import com.google.gson.internal.LinkedTreeMap;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("activity-extraction")
public class ActivityExtractionResource {

  @Inject
  private ActivityExtractionFacade activityExtractionFacade;

  @PUT
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public String createOrUpdateActivityExtraction(@PathParam("id") String activityId) {
    activityExtractionFacade.createOrUpdateActivityExtraction(activityId);
    return new Response().buildSuccess().toJson();
  }

  @DELETE
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public String deleteActivityExtraction(@PathParam("id") String activityId) {
    activityExtractionFacade.deleteActivityExtraction(activityId);
    return new Response().buildSuccess().toJson();
  }

  @GET
  @SecuredExtraction
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/{acronym}/{version}")
  public byte[] getSurveyActivitiesExtractionAsCsv(@PathParam("acronym") String acronym, @PathParam("version") Integer version) {
    return activityExtractionFacade.getSurveyActivitiesExtractionAsCsv(acronym, version);
  }

  @GET
  @SecuredExtraction
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/json/{acronym}/{version}")
  public String getSurveyActivitiesExtractionAsJson(@PathParam("acronym") String acronym, @PathParam("version") Integer version) {
    ArrayList<LinkedTreeMap> json =  activityExtractionFacade.getSurveyActivitiesExtractionAsJson(acronym, version);
    return new Response().buildSuccess(json).toJson();
  }

  @POST
  @SecuredExtraction
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/rscript")
  public byte[] getRscriptSurveyExtraction(String rscriptSurveyExtractionJson) {
    return activityExtractionFacade.getRscriptSurveyExtraction(rscriptSurveyExtractionJson);
  }
}
