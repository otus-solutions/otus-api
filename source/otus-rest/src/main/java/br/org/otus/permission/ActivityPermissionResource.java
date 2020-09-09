package br.org.otus.permission;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;

import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;
import br.org.otus.survey.activity.permission.ActivityAccessPermissionFacade;

@Path("/permission")
public class ActivityPermissionResource {

  @Inject
  private ActivityAccessPermissionFacade activityAccessPermissionFacade;

  @POST
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String permission) {
    activityAccessPermissionFacade.create(ActivityAccessPermission.deserialize(permission));
    return new Response().buildSuccess().toJson();
  }

  @PUT
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(String permission) {
    activityAccessPermissionFacade.update(ActivityAccessPermission.deserialize(permission));
    return new Response().buildSuccess().toJson();
  }

  @GET
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public String getAll() {
    return new Response().buildSuccess(activityAccessPermissionFacade.getAll()).toJson(ActivityAccessPermission.getGsonBuilder());
  }

  @GET
  @Path("/{acronym}/{version}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@PathParam("acronym") String acronym, @PathParam("version") String version) {
    return new Response().buildSuccess(activityAccessPermissionFacade.get(acronym, version)).toJson(ActivityAccessPermission.getGsonBuilder());
  }

}
