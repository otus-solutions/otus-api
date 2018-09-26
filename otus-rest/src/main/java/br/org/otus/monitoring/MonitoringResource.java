package br.org.otus.monitoring;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("/monitoring")
public class MonitoringResource {

  @Inject
  private MonitoringFacade monitoringFacade;

  @GET
  @Secured
  @Path("/activities")
  @Produces(MediaType.APPLICATION_JSON)
  public String listActivities() {
    return new Response().buildSuccess(monitoringFacade.listActivities()).toJson();
  }

  @GET
  @Secured
  @Path("/activities/{acronym}")
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@PathParam("acronym") String acronym) {
    return new Response().buildSuccess(monitoringFacade.get(acronym)).toJson();
  }


  @GET
  @Secured
  @Path("/centers")
  @Produces(MediaType.APPLICATION_JSON)
  public String getMonitoring() {
    return new Response().buildSuccess(monitoringFacade.getMonitoringCenters()).toJson();
  }


  //TODO 19/09/18: review below
  @GET
  @Secured
  @Path("/activities/status/{center}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getStatus(@PathParam("center") String acronym) {
    return new Response().buildSuccess(monitoringFacade.get(acronym)).toJson();
  }

  @GET
//  @Secured //TODO 19/09/18: uncomment
  @Path("/activities/status/")
  @Produces(MediaType.APPLICATION_JSON)
  public String getProjectStatus() {
    monitoringFacade.getActivityStatus();
    return new Response().buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("/activities/status/{rn}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getParticipantStatus(@PathParam("rn") String rn) {
    return new Response().buildSuccess(monitoringFacade.get(rn)).toJson();
  }


}
