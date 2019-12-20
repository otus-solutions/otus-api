package br.org.otus.user;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import br.org.otus.user.dto.pendency.UserActivityPendencyDto;

@Path("/pendency/user-activity-pendency")
public class UserActivityPendencyResource {

//  @Inject
//  private UserActivityPendencyFacade userActivityPendencyFacade;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(UserActivityPendencyDto userActivityPendencyDto) {
      Response response = new Response();
//      userActivityPendencyFacade.create(userActivityPendencyDto);
//      return response.buildSuccess().toJson();
      return null;
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(UserActivityPendencyDto userActivityPendencyDto) {
    Response response =  new Response();
//    userActivityPendencyFacade.update(userActivityPendencyDto);
//    return response.buildSuccess().toJson();
    return null;
  }

  @GET
  @Secured
  @Path("/{id}/{state}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@PathParam("id") String id, @PathParam("state") String state) {
    Response response = new Response();
//    List<ManagementUserDto> managementUserDtos = userActivityPendencyFacade.list();
//    return response.buildSuccess(managementUserDtos).toJson();
    return response.buildSuccess("I am here!").toJson();
  }

  @DELETE
  @Secured
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String deleteById(@PathParam("id") String id) {
//    return new Response().buildSuccess(userActivityPendencyFacade.deleteById(id)).toJson();
    return null;
  }

}

