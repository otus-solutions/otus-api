package br.org.otus.configuration.project;

import br.org.otus.project.configuration.api.ProjectConfigurationFacade;
import br.org.otus.rest.Response;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("configuration/project")
public class ProjectConfigurationResource {

	@Inject
	private ProjectConfigurationFacade projectConfigurationFacade;

	@GET
//	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String getProjectConfiguration() {
		//TODO 19/07/18: review toJson
		return new Response().buildSuccess(projectConfigurationFacade.getProjectConfiguration()).toJson();
	}

	@PUT
//	@Secured TODO: uncomment
	@Path("/participant/registration/{permission}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String allowNewParticipants(@PathParam("permission") boolean permission) {
		projectConfigurationFacade.enableNewParticipants(permission);
		return new Response().buildSuccess().toJson();
	}

}
