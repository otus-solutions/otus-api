package br.org.otus.laboratory;

import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import br.org.otus.model.User;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.user.Secured;
import br.org.otus.user.api.UserFacade;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/unattached-laboratory")
public class UnattachedLaboratoryResource {

  @Inject
  private UnattachedLaboratoryFacade unattachedLaboratoryFacade;
  @Inject
  private UserFacade userFacade;
  @Inject
  private SecurityContext securityContext;

  @PUT
  @Secured
  @Path("/create/{acronym}/{descriptorName}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String initialize(@Context HttpServletRequest request, @PathParam("acronym") String fieldCenterAcronym, @PathParam("descriptorName") String collectGroupDescriptorName){
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    User user = userFacade.fetchByEmail(userEmail);
    unattachedLaboratoryFacade.create(user.getEmail(), fieldCenterAcronym, collectGroupDescriptorName);
    return new Response().buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("/{acronym}/{descriptorName}/{page}/{quantity}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getUnattached(@PathParam("acronym") String fieldCenterAcronym, @PathParam("descriptorName") String collectGroupDescriptorName, @PathParam("page") int page, @PathParam("quantity") int quantityByPage){
    ListUnattachedLaboratoryDTO listUnattachedLaboratoryDTO = unattachedLaboratoryFacade.find(fieldCenterAcronym, collectGroupDescriptorName, page, quantityByPage);
    return new Response().buildSuccess(ListUnattachedLaboratoryDTO.serializeToJsonTree(listUnattachedLaboratoryDTO)).toJson();
  }

  @GET
  @Secured
  @Path("/{laboratoryOid}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getUnattached(@PathParam("laboratoryOid") String laboratoryOid){
    UnattachedLaboratory unattachedLaboratory = unattachedLaboratoryFacade.findById(laboratoryOid);
    return new Response().buildSuccess(UnattachedLaboratory.serializeToJsonTree(unattachedLaboratory)).toJson();
  }

  @POST
  @Secured
  @Path("/attache/{laboratoryIdentification}/{recruitmentNumber}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String attache(@Context HttpServletRequest request, @PathParam("laboratoryIdentification") int laboratoryIdentification, @PathParam("recruitmentNumber") Long recruitmentNumber){
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    User user = userFacade.fetchByEmail(userEmail);
    unattachedLaboratoryFacade.attache(user.getEmail(), laboratoryIdentification, recruitmentNumber);
    return new Response().buildSuccess().toJson();
  }

  @DELETE
  @Secured
  @Path("/{laboratoryOid}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String discard(@Context HttpServletRequest request, @PathParam("laboratoryOid") String laboratoryOid){
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    User user = userFacade.fetchByEmail(userEmail);
    unattachedLaboratoryFacade.discard(user.getEmail(), laboratoryOid);
    return new Response().buildSuccess().toJson();
  }
}