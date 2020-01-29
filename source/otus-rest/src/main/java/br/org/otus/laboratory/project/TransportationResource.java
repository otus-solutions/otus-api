package br.org.otus.laboratory.project;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.project.api.TransportationLotFacade;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
import br.org.otus.security.context.SecurityContext;
import com.google.gson.GsonBuilder;
import org.json.JSONException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;

@Path("/laboratory-project/transportation")
public class TransportationResource {

  @Inject
  private TransportationLotFacade transportationLotFacade;

  @Inject
  private SecurityContext securityContext;

  @GET
  @Secured
  @Path("/lots")
  public String getLots() {
    List<TransportationLot> lots = transportationLotFacade.getLots();
    GsonBuilder builder = TransportationLot.getGsonBuilder();
    return new Response().buildSuccess(builder.create().toJson(lots)).toJson();
  }

  @POST
  @Secured
  @Path("/lot")
  public String create(@Context HttpServletRequest request, String transportationLotJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

    TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
    TransportationLot createdLot = transportationLotFacade.create(transportationLot, userEmail);
    return new Response().buildSuccess(TransportationLot.serialize(createdLot)).toJson();
  }

  @PUT
  @Secured
  @Path("/lot")
  public String update(String transportationLotJson) {
    TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
    TransportationLot updatedLot = transportationLotFacade.update(transportationLot);
    return new Response().buildSuccess(TransportationLot.serialize(updatedLot)).toJson();
  }

  @DELETE
  @Secured
  @Path("/lot/{id}")
  public String delete(@PathParam("id") String code) {
    transportationLotFacade.delete(code);
    return new Response().buildSuccess().toJson();
  }

  @POST
  @Secured
  @Path("/aliquots")
  public String getAliquotsByPeriod(@Context HttpServletRequest request, String filterAliquotJson) throws JSONException {

    TransportationAliquotFiltersDTO transportationAliquotFiltersDTO = TransportationAliquotFiltersDTO.deserialize(filterAliquotJson);
    List<Aliquot> aliquots = transportationLotFacade.getAliquotsByPeriod(transportationAliquotFiltersDTO);
    GsonBuilder builder = Aliquot.getGsonBuilder();

    return new Response().buildSuccess(builder.create().toJson(aliquots)).toJson();
  }

  @POST
  @Secured
  @Path("/aliquot")
  public String getAliquot(@Context HttpServletRequest request, String filterAliquotJson) {

    TransportationAliquotFiltersDTO transportationAliquotFiltersDTO = TransportationAliquotFiltersDTO.deserialize(filterAliquotJson);
    Aliquot aliquot = transportationLotFacade.getAliquot(transportationAliquotFiltersDTO);
    GsonBuilder builder = Aliquot.getGsonBuilder();

    return new Response().buildSuccess(builder.create().toJson(aliquot)).toJson();
  }

  @GET
  @Secured
  @Path("/aliquots")
  public String getAliquots() {
    List<Aliquot> aliquots = transportationLotFacade.getAliquots();
    GsonBuilder builder = TransportationLot.getGsonBuilder();
    return new Response().buildSuccess(builder.create().toJson(aliquots)).toJson();
  }
}
