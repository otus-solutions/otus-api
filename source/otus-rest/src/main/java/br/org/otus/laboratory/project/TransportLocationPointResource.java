package br.org.otus.laboratory.project;

import br.org.otus.laboratory.project.api.TransportLocationPointFacade;
import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import br.org.otus.laboratory.project.transportation.persistence.TransportLocationPointListDTO;
import br.org.otus.model.User;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
import br.org.otus.security.context.SecurityContext;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/laboratory-project/transport-location-point")
public class TransportLocationPointResource {

  @Inject
  private TransportLocationPointFacade transportLocationPointFacade;

  @Inject
  private SecurityContext securityContext;

  @GET
  @Secured
  @Path("/configuration")
  public String getConfiguration() {
    TransportLocationPointListDTO transportLocationPointListDTO = transportLocationPointFacade.getLocationList();
    return new Response().buildSuccess(TransportLocationPointListDTO.serializeToJsonTree(transportLocationPointListDTO)).toJson();
  }

  @PUT
  @Secured
  @Path("/{locationName}")
  public String createLocationPoint(@PathParam("locationName") String locationName) {
    TransportLocationPoint transportLocationPoint = new TransportLocationPoint(locationName);
    transportLocationPointFacade.createLocationPoint(transportLocationPoint);
    return new Response().buildSuccess(TransportLocationPoint.serializeToJsonTree(transportLocationPoint)).toJson();
  }

  @POST
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateLocationPoint(String locationJson) {
    TransportLocationPoint transportLocationPoint = TransportLocationPoint.deserialize(locationJson);
    transportLocationPointFacade.updateLocationPoint(transportLocationPoint);
    return new Response().buildSuccess(TransportLocationPoint.serializeToJsonTree(transportLocationPoint)).toJson();
  }

  @DELETE
  @Secured
  @Path("/{locationPointId}")
  public String deleteLocationPoint(@PathParam("locationPointId") String locationPointId) {
    transportLocationPointFacade.deleteLocationPoint(locationPointId);
    return new Response().buildSuccess().toJson();
  }

  @POST
  @Secured
  @Path("/add-user/{locationPointId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String addUserToLocationPoint(@PathParam("locationPointId") String locationPointId,String userJson) {
    User user = User.deserialize(userJson);
    transportLocationPointFacade.addUser(locationPointId, user);
    return new Response().buildSuccess().toJson();
  }

  @POST
  @Secured
  @Path("/remove-user/{locationPointId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String removeUserToLocationPoint(@PathParam("locationPointId") String locationPointId,String userJson) {
    User user = User.deserialize(userJson);
    transportLocationPointFacade.removeUser(locationPointId, user);
    return new Response().buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("/user-location-points")
  public String getUserLocationPoint(@Context HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

    TransportLocationPointListDTO userLocationPoints = transportLocationPointFacade.getUserLocationPoints(userEmail);
    return new Response().buildSuccess(TransportLocationPointListDTO.serializeToJsonTree(userLocationPoints)).toJson();
  }

  @GET
  @Secured
  @Path("/location-points")
  public String getLocationPoints(@Context HttpServletRequest request) {
    TransportLocationPointListDTO userLocationPoints = transportLocationPointFacade.getLocationPoints();
    return new Response().buildSuccess(TransportLocationPointListDTO.serializeToJsonTree(userLocationPoints)).toJson();
  }
}
