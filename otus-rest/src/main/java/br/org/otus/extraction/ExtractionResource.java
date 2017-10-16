package br.org.otus.extraction;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import br.org.otus.user.api.UserFacade;
import br.org.otus.user.dto.ManagementUserDto;

@Path("/data-extraction")
public class ExtractionResource {

    @Inject
    private UserFacade userFacade;

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
}
