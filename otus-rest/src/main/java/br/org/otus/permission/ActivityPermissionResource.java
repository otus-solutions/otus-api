package br.org.otus.permission;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import br.org.otus.survey.activity.permission.ActivityAccessPermissionFacade;

@Path("/permission")
public class ActivityPermissionResource {

  @Inject
  private ActivityAccessPermissionFacade activityAccessPermissionFacade;
  @Inject
  private ActivityAccessPermission activityAccessPermission;

  @POST
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String permission) {
    activityAccessPermissionFacade.create(ActivityAccessPermission.deserialize(permission));
    return new Response().buildSuccess().toJson();
  }

  @GET
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public String getAll() {
    return new Response().buildSuccess(activityAccessPermissionFacade.getAll()).toJson(ActivityAccessPermission.getGsonBuilder());
  }

  @PUT
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(String permission) {
    activityAccessPermissionFacade.update(ActivityAccessPermission.deserialize(permission));
    return new Response().buildSuccess().toJson();
  }
}
