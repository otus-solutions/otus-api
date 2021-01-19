package br.org.otus.configuration.project;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.project.configuration.api.ProjectConfigurationFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;

@Path("/configuration/project")
public class ProjectConfigurationResource {

  @Inject
  private ProjectConfigurationFacade projectConfigurationFacade;

  @GET
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public String getProjectConfiguration() {
    return new Response().buildSuccess(projectConfigurationFacade.getProjectConfiguration()).toJson();
  }

  @PUT
  @Secured
  @Path("/participant/registration/{permission}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String allowNewParticipants(@PathParam("permission") boolean permission) {
    projectConfigurationFacade.enableParticipantRegistration(permission);
    return new Response().buildSuccess().toJson();
  }

  @PUT
  @Secured
  @Path("/participant/autoGenerateRecruitmentNumber/{permission}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String autoGenerateRecruitmentNumber(@PathParam("permission") boolean permission) {
    projectConfigurationFacade.autoGenerateRecruitmentNumber(permission);
    return new Response().buildSuccess().toJson();
  }

  @PUT
  @Secured
  @Path("/participant/addressCensusRequired/{permission}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String addressCensusRequired(@PathParam("permission") boolean permission) {
    projectConfigurationFacade.addressCensusRequired(permission);
    return new Response().buildSuccess().toJson();
  }

}
