package br.org.otus.extraction.rest;

import br.org.otus.extraction.RscriptFacade;
import br.org.otus.extraction.SecuredExtraction;
import br.org.otus.rest.Response;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("data-extraction/rscript")
public class RscriptResource {

  @Inject
  private RscriptFacade rscriptFacade;

  @PUT
  @SecuredExtraction
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String createOrUpdate(String rscriptJson) {
    rscriptFacade.createOrUpdate(rscriptJson);
    return new Response().buildSuccess().toJson();
  }

  @GET
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{name}")
  public String get(@PathParam("name") String rscriptName) {
    return new Response().buildSuccess(rscriptFacade.get(rscriptName)).toJson();
  }

  @DELETE
  @SecuredExtraction
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{name}")
  public String delete(@PathParam("name") String rscriptName) {
    rscriptFacade.delete(rscriptName);
    return new Response().buildSuccess().toJson();
  }
}
