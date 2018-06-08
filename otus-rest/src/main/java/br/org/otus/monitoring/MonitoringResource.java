package br.org.otus.monitoring;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.model.ReportTemplate;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
}
