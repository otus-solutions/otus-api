package br.org.otus.laboratory.configuration;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;

@Path("/laboratory-configuration")
public class LaboratoryConfigurationResource {

  @Inject
  private LaboratoryConfigurationFacade laboratoryConfigurationFacade;

  @Inject
  private LaboratoryConfigurationService laboratoryConfigurationService;

  @GET
  @Secured
  @Path("/exists")
  public String getCheckingExist() {
    return new Response().buildSuccess(laboratoryConfigurationService.getCheckingExist()).toJson();
  }

  @GET
  @Secured
  @Path("/descriptor")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getDescriptor() {
    LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationService.getLaboratoryConfiguration();
    LaboratoryConfigurationDTO laboratoryConfigurationDTO = new LaboratoryConfigurationDTO(laboratoryConfiguration);
    return new Response().buildSuccess(laboratoryConfigurationDTO).toJson();
  }

  @GET
  @Secured
  @Path("/aliquot-configuration")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getAliquotConfiguration() {
    AliquotConfiguration aliquotConfiguration = laboratoryConfigurationService.getAliquotConfiguration();
    return new Response().buildSuccess(aliquotConfiguration).toJson();
  }

  @GET
  @Secured
  @Path("/aliquot-descriptors")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getAliquotDescriptors() {
    List<AliquoteDescriptor> aliquoteDescriptors = laboratoryConfigurationService.getAliquotDescriptors();
    return new Response().buildSuccess(aliquoteDescriptors).toJson();
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
}
