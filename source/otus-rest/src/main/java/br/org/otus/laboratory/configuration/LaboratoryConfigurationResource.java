package br.org.otus.laboratory.configuration;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.LotReceiptCustomMetadata;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;

@Path("/laboratory-configuration")
public class LaboratoryConfigurationResource {

  @Inject
  private LaboratoryConfigurationFacade laboratoryConfigurationFacade;

  @GET
  @Secured
  @Path("/exists")
  public String getCheckingExist() {
    return new Response().buildSuccess(laboratoryConfigurationFacade.getCheckingExist()).toJson();
  }

  @GET
  @Secured
  @Path("/descriptor")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getDescriptor() {
    return new Response().buildSuccess(laboratoryConfigurationFacade.getLaboratoryConfiguration()).toJson();
  }

  @GET
  @Secured
  @Path("/aliquot-configuration")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getAliquotConfiguration() {
    return new Response().buildSuccess(laboratoryConfigurationFacade.getAliquotConfiguration()).toJson();
  }

  @GET
  @Secured
  @Path("/aliquot-descriptors")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getAliquotDescriptors() {
    return new Response().buildSuccess(laboratoryConfigurationFacade.getAliquotDescriptors()).toJson();
  }

  @GET
  @Secured
  @Path("/tube-custom-metadata/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String getTubeMedataData(@PathParam("type") String tubeType) {
    List<TubeCustomMetadata> tubeCustomMetadata = laboratoryConfigurationFacade.getTubeMedataData(tubeType);
    return new Response().buildSuccess(tubeCustomMetadata).toJson(TubeCustomMetadata.getGsonBuilder());
  }

  @GET
  @Secured
  @Path("/lot-receipt-custom-metadata")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String getLotReceiptCustomMetadata() {
    List<LotReceiptCustomMetadata> receiptCustomMetadata = laboratoryConfigurationFacade.getLotReceiptCustomMetadata();
    return new Response().buildSuccess(receiptCustomMetadata).toJson(LotReceiptCustomMetadata.getGsonBuilder());
  }
}
