package br.org.otus.user;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import br.org.otus.user.api.UserFacade;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.otus.user.dto.SignupDataDto;

@Path("/user")
public class UserResource {

  @Inject
  private UserFacade userFacade;

  @POST
  @Path("/signup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String signup(SignupDataDto signupDataDto) {
    try {
      signupDataDto.encrypt();
      Response response = new Response();
      userFacade.create(signupDataDto);
      return response.buildSuccess().toJson();

    } catch (EncryptedException e) {
      throw new HttpResponseException(Validation.build());
    }
  }

  @GET
  @Secured
  @Path("/list")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String list() {
    Response response = new Response();
    List<ManagementUserDto> managementUserDtos = userFacade.list();
    return response.buildSuccess(managementUserDtos).toJson();
  }

  @POST
  @Secured
  @Path("/disable")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String disableUsers(ManagementUserDto managementUserDto) {
    Response response = new Response();
    userFacade.disable(managementUserDto);
    return response.buildSuccess().toJson();
  }

  @POST
  @Path("/field-center")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateFieldCenter(ManagementUserDto managementUserDto) {
    Response response = new Response();
    userFacade.updateFieldCenter(managementUserDto);
    return response.buildSuccess().toJson();
  }

  @POST
  @Secured
  @Path("/enable")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String enableUsers(ManagementUserDto managementUserDto) {
    Response response = new Response();
    userFacade.enable(managementUserDto);
    return response.buildSuccess().toJson();
  }

  @POST
//  @Secured todo: uncommet
  @Path("/reset-password")
  @Produces(MediaType.TEXT_PLAIN)
  public String getToken(String email, @Context HttpServletRequest request) {
    Response response = new Response();
    String requestAddress = request.getRemoteAddr().toString();

    String scheme = request.getScheme();
    String hostPath = scheme + "://" + request.getHeader("Host");

    userFacade.requestPasswordReset(email, requestAddress, hostPath);
    return response.buildSuccess(email).toJson();
  }
}
