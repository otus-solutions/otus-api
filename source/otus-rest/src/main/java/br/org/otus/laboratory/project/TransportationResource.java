package br.org.otus.laboratory.project;

import br.org.otus.laboratory.configuration.lot.receipt.MaterialReceiptCustomMetadata;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.project.api.TransportationLotFacade;
import br.org.otus.laboratory.project.transportation.ReceivedMaterial;
import br.org.otus.laboratory.project.transportation.TrailHistoryRecord;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.model.TransportationReceipt;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
import br.org.otus.security.context.SecurityContext;
import com.google.gson.GsonBuilder;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
  @Path("/lots/from/{originLocationPointId}")
  public String getLotsByOrigin(@PathParam("originLocationPointId") String originLocationPointId) {
    List<TransportationLot> lots = transportationLotFacade.getLots(originLocationPointId, null);
    GsonBuilder builder = TransportationLot.getGsonBuilder();
    return new Response().buildSuccess(builder.create().toJson(lots)).toJson();
  }

  @GET
  @Secured
  @Path("/lots/to/{destinationLocationPointId}")
  public String getLotsByDestination(@PathParam("destinationLocationPointId") String destinationLocationPointId) {
    List<TransportationLot> lots = transportationLotFacade.getLots(null, destinationLocationPointId);
    GsonBuilder builder = TransportationLot.getGsonBuilder();
    return new Response().buildSuccess(builder.create().toJson(lots)).toJson();
  }

  @GET
  @Secured
  @Path("/lots/{originLocationPointId}/{destinationLocationPointId}")
  public String getLots(@PathParam("originLocationPointId") String originLocationPointId, @PathParam("destinationLocationPointId") String destinationLocationPointId) {
    List<TransportationLot> lots = transportationLotFacade.getLots(originLocationPointId, destinationLocationPointId);
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
  public String update(@Context HttpServletRequest request, String transportationLotJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

    TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
    TransportationLot updatedLot = transportationLotFacade.update(transportationLot,userEmail);
    return new Response().buildSuccess(TransportationLot.serialize(updatedLot)).toJson();
  }

  @POST
  @Secured
  @Path("/lot/receipt/{code}")
  public String updateLotReceipt(@Context HttpServletRequest request, @PathParam("code") String code, String transportationReceiptJson) throws DataNotFoundException {
    transportationLotFacade.receiveLot(code, TransportationReceipt.deserialize(transportationReceiptJson));
    return new Response().buildSuccess().toJson();
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
  @Path("/aliquots/{locationPointId}")
  public String getAliquotsByPeriod(@Context HttpServletRequest request, @PathParam("locationPointId") String locationPointId, String filterAliquotJson) throws JSONException {

    TransportationAliquotFiltersDTO transportationAliquotFiltersDTO = TransportationAliquotFiltersDTO.deserialize(filterAliquotJson);
    List<Aliquot> aliquots = transportationLotFacade.getAliquotsByPeriod(transportationAliquotFiltersDTO, locationPointId);
    GsonBuilder builder = Aliquot.getGsonBuilder();

    return new Response().buildSuccess(builder.create().toJson(aliquots)).toJson();
  }

  @POST
  @Secured
  @Path("/aliquot/{locationPointId}")
  public String getAliquot(@Context HttpServletRequest request, @PathParam("locationPointId") String locationPointId, String filterAliquotJson) {

    TransportationAliquotFiltersDTO transportationAliquotFiltersDTO = TransportationAliquotFiltersDTO.deserialize(filterAliquotJson);
    Aliquot aliquot = transportationLotFacade.getAliquot(transportationAliquotFiltersDTO, locationPointId);
    GsonBuilder builder = Aliquot.getGsonBuilder();

    return new Response().buildSuccess(builder.create().toJson(aliquot)).toJson();
  }

  @GET
  @Secured
  @Path("/tube/{locationPointId}/{tubeCode}")
  public String getTube(@PathParam("locationPointId") String locationPointId, @PathParam("tubeCode") String tubeCode) {
    Tube tube = transportationLotFacade.getTube(locationPointId, tubeCode);
    return new Response().buildSuccess(Tube.serializeToJsonTree(tube)).toJson();
  }

  @POST
  @Secured
  @Path("/lot/{id}/receive-material")
  public String receiveMaterial(@Context HttpServletRequest request, @PathParam("id") String transportationLotId, String receiveMaterialJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

    ReceivedMaterial receivedMaterial = ReceivedMaterial.deserialize(receiveMaterialJson);
    transportationLotFacade.receiveMaterial(receivedMaterial, userEmail, transportationLotId);
    return new Response().buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("/material/tracking/{materialCode}")
  public String getMaterialTrackingList(@Context HttpServletRequest request, @PathParam("materialCode") String materialCode) {
    return new Response().buildSuccess(TrailHistoryRecord.getGsonBuilder().create().toJsonTree(transportationLotFacade.getMaterialTrackingList(materialCode))).toJson();
  }
}
