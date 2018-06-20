package br.org.otus.monitoring;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.model.ReportTemplate;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/monitoring")
public class MonitoringResource {

    @Inject
    private MonitoringFacade monitoringFacade;
    
    

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public String list() {
        return new Response().buildSuccess(monitoringFacade.list()).toCustomJson(ReportTemplate.getGsonBuilder());
    }
    
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
    
    
}
