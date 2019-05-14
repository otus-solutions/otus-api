package br.org.otus.permission;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.laboratory.configuration.permission.LaboratoryAccessPermission;
import br.org.otus.laboratory.permission.LaboratoryAccessPermissionFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("/permission")
public class LaboratoryPermissionResource {

  @Inject
  private LaboratoryAccessPermissionFacade permission;

  @POST
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String permission) {
    this.permission.create(LaboratoryAccessPermission.deserialize(permission));
    return new Response().buildSuccess().toJson();
  }

  @GET
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public String getAll() {
    return new Response().buildSuccess(this.permission.getAll()).toJson(LaboratoryAccessPermission.getGsonBuilder());
  }

  @PUT
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(String permission) {
    this.permission.update(LaboratoryAccessPermission.deserialize(permission));
    return new Response().buildSuccess().toJson();
  }

}
