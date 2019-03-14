package br.org.otus.permission;

import br.org.otus.rest.Response;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/permission")
public class PermissionResource {

    @Inject
    private PermissionFacade permissionFacade;

    @GET
    @Path("/user/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll(@PathParam("email") String email) {
        return new Response().buildSuccess().toJson();
    }

    @POST
    @Path("/user/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String savePermission(String permissionJson) {
        return new Response().buildSuccess().toJson();
    }
}
