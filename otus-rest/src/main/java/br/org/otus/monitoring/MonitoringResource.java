package br.org.otus.monitoring;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.model.monitoring.ActivitiesProgressReport;

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

  @GET
  @Secured
  @Path("/activities/progress/")
  @Produces(MediaType.APPLICATION_JSON)
  public String getProjectStatus() {
    return new Response().buildSuccess(monitoringFacade.getActivitiesProgress()).toJson();
  }

  @GET
  @Secured
  @Path("/activities/progress/{center}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getActivitiesProgress(@PathParam("center") String center) {
    return new Response().buildSuccess(monitoringFacade.getActivitiesProgress(center)).toJson(ActivitiesProgressReport.getGsonBuilder());
  }


}
