package br.org.otus.survey.activity;

import br.org.otus.model.User;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.user.Secured;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.user.api.UserFacade;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollectionGroupsDTO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("offline/activities")
public class OfflineActivityResource {

  @Inject
  private ActivityFacade activityFacade;
  @Inject
  private UserFacade userFacade;
  @Inject
  private SecurityContext securityContext;

  @PUT
  @Secured
  @Path("collection")
  @Consumes(MediaType.APPLICATION_JSON)
  public String saveOffline(@Context HttpServletRequest request, String offlineActivityCollections) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    OfflineActivityCollection offlineActivityCollection = OfflineActivityCollection.deserialize(offlineActivityCollections);
    offlineActivityCollection.setAvailableToSynchronize(true);
    activityFacade.save(userEmail, offlineActivityCollection);
    return new Response().buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("collection")
  @Consumes(MediaType.APPLICATION_JSON)
  public String fetchOfflineCollections(@Context HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    OfflineActivityCollectionGroupsDTO offlineActivityCollectionsDTO = activityFacade.fetchOfflineActivityCollections(userEmail);
    return new Response().buildSuccess(OfflineActivityCollectionGroupsDTO.serializeToJsonTree(offlineActivityCollectionsDTO)).toJson();
  }

  @POST
  @Secured
  @Path("synchronize/{recruitmentNumber}/{offlineCollectionId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String synchronizeOfflineActivities(@Context HttpServletRequest request, @PathParam("recruitmentNumber") long recruitmentNumber, @PathParam("offlineCollectionId") String offlineCollectionId) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    User user = userFacade.fetchByEmail(userEmail);
    activityFacade.synchronize(recruitmentNumber, offlineCollectionId, user.get_id());
    return new Response().buildSuccess().toJson();
  }
}
