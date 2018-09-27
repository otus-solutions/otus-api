package br.org.otus.survey.activity.permission;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("/permission")
public class ActivityAccessPermissionResource {
  
  @Inject
  private ActivityAccessPermissionFacade activityAccessPermissionFacade;
  
  @POST
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(FieldCenter fieldCenter) {
    //activityAccessPermissionFacade.create(fieldCenter);
      return new Response().buildSuccess().toJson();
  }
  
  @GET
  @Secured
  @Path("/list")    
  @Produces(MediaType.APPLICATION_JSON)
  public String getAll() {
      Response response = new Response();
//      List<ActivityAccessPermission> activityAccessPermissions = activityAccessPermissionFacade.list();
//      return response.setData(ActivityAccessPermissions).toJson();
      return null;
  }
  
  @POST
  @Secured
  @Path("/update")
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(ActivityAccessPermission permission) {
//    activityAccessPermissionFacade.update(permission);
      return new Response().buildSuccess().toJson();
  }
  
  
  
  
  
  

}
