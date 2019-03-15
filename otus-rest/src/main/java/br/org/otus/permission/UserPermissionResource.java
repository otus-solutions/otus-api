package br.org.otus.permission;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bson.Document;

import br.org.otus.rest.Response;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;

import java.util.Arrays;

@Path("/permission")
public class UserPermissionResource {

  @Inject
  private PermissionFacade permissionFacade;

  @GET
  @Path("/user/{email}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getAll(@PathParam("email") String email) {
    return new Response().buildSuccess(permissionFacade.getAll(email)).toJson();
  }

  @POST
  @Path("/user/save")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String savePermission(String permissionJson) {
    UserPermissionDTO userPermissionDTO = UserPermissionDTO.deserializeSinglePermission(permissionJson);
    return new Response().buildSuccess(permissionFacade.savePermission(userPermissionDTO)).toJson();
  }
}
