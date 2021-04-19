package br.org.otus.laboratory.configurationCrud;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationFacade;
import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.LotReceiptCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.MaterialReceiptCustomMetadata;
import br.org.otus.laboratory.configurationCrud.model.*;
import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/laboratory-configuration-crud")
public class LaboratoryConfigurationCrudResource {

  @Inject
  private LaboratoryConfigurationCrudFacade laboratoryConfigurationCrudFacade;

  @POST
  @Secured
  @Path("/tube")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createTube(String tubeJson) {
    TubeConfiguration tubeConfiguration = TubeConfiguration.deserialize(tubeJson);
    laboratoryConfigurationCrudFacade.createTube(tubeConfiguration);
    return new Response().buildSuccess(TubeConfiguration.serialize(tubeConfiguration)).toJson();
  }

  @GET
  @Secured
  @Path("/tube")
  @Produces(MediaType.APPLICATION_JSON)
  public String indexTube() throws DataNotFoundException {
    ArrayList<TubeConfiguration> tubeConfigurations = laboratoryConfigurationCrudFacade.indexTube();
    return new Response().buildSuccess(tubeConfigurations).toJson(TubeConfiguration.getGsonBuilder());
  }


  @POST
  @Secured
  @Path("/aliquot")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createAliquot(String aliquotJson) {
    AliquotConfiguration aliquotConfiguration = AliquotConfiguration.deserialize(aliquotJson);
    laboratoryConfigurationCrudFacade.createAliquot(aliquotConfiguration);
    return new Response().buildSuccess(AliquotConfiguration.serialize(aliquotConfiguration)).toJson();
  }

  @GET
  @Secured
  @Path("/aliquot")
  @Produces(MediaType.APPLICATION_JSON)
  public String indexAliquot() throws DataNotFoundException {
    ArrayList<AliquotConfiguration> aliquotConfigurations = laboratoryConfigurationCrudFacade.indexAliquot();
    return new Response().buildSuccess(aliquotConfigurations).toJson(AliquotConfiguration.getGsonBuilder());
  }

  @POST
  @Secured
  @Path("/moment")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createMoment(String momentJson) {
    MomentConfiguration momentConfiguration = MomentConfiguration.deserialize(momentJson);
    laboratoryConfigurationCrudFacade.createMoment(momentConfiguration);
    return new Response().buildSuccess(MomentConfiguration.serialize(momentConfiguration)).toJson();
  }

  @GET
  @Secured
  @Path("/moment")
  @Produces(MediaType.APPLICATION_JSON)
  public String indexMoment() throws DataNotFoundException {
    ArrayList<MomentConfiguration> momentConfigurations = laboratoryConfigurationCrudFacade.indexMoment();
    return new Response().buildSuccess(momentConfigurations).toJson(MomentConfiguration.getGsonBuilder());
  }

  @POST
  @Secured
  @Path("/exam")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createExam(String examJson) {
    ExamConfiguration examConfiguration = ExamConfiguration.deserialize(examJson);
    laboratoryConfigurationCrudFacade.createExam(examConfiguration);
    return new Response().buildSuccess(ExamConfiguration.serialize(examConfiguration)).toJson();
  }

  @GET
  @Secured
  @Path("/exam")
  @Produces(MediaType.APPLICATION_JSON)
  public String indexExam() throws DataNotFoundException {
    ArrayList<ExamConfiguration> examConfigurations = laboratoryConfigurationCrudFacade.indexExam();
    return new Response().buildSuccess(examConfigurations).toJson(ExamConfiguration.getGsonBuilder());
  }

  @POST
  @Secured
  @Path("/controll-group")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createControllGroup(String controllGroupJson) {
    ControllGroupConfiguration controllGroupConfiguraiton =
            ControllGroupConfiguration.deserialize(controllGroupJson);
    laboratoryConfigurationCrudFacade.createControllGroup(controllGroupConfiguraiton);
    return new Response().buildSuccess(ControllGroupConfiguration.serialize(controllGroupConfiguraiton)).toJson();
  }

  @GET
  @Secured
  @Path("/controll-group")
  @Produces(MediaType.APPLICATION_JSON)
  public String indexControllGroup() throws DataNotFoundException {
    ArrayList<ControllGroupConfiguration> controllGroupConfiguraitons =
            laboratoryConfigurationCrudFacade.indexControllGroup();
    return new Response().buildSuccess(controllGroupConfiguraitons).toJson(ControllGroupConfiguration.getGsonBuilder());
  }
}
