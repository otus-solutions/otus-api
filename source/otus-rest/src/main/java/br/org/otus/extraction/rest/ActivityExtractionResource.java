package br.org.otus.extraction.rest;

import br.org.otus.extraction.ExtractionFacade;
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
  private ExtractionFacade extractionFacade;

  @POST
  @SecuredExtraction
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/pipeline/json/{pipeline}")
  public String extractJsonFromPipeline(String pipelineDtoJson) {
    ArrayList<LinkedTreeMap> json =  extractionFacade.createJsonExtractionFromPipeline(pipelineDtoJson);
    return new Response().buildSuccess(json).toJson();
  }

  @POST
  @SecuredExtraction
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/pipeline/csv")
  public byte[] extractCsvFromPipeline(String pipelineDtoJson) {
    return extractionFacade.createCsvExtractionFromPipeline(pipelineDtoJson);
  }

  @PUT
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/activity/{id}")
  public String createOrUpdateActivityExtraction(@PathParam("id") String activityId) {
    extractionFacade.createOrUpdateActivityExtraction(activityId);
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
