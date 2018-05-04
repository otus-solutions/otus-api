package br.org.otus.configuration.survey;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import br.org.otus.survey.api.SurveyFacade;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;

@Path("configuration/surveys")
public class SurveyResource {

	@Inject
	private SurveyFacade surveyFacade;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllUndiscarded() {
		return new Response().buildSuccess(surveyFacade.listUndiscarded()).toSurveyJson();
	}


	//TODO 03/05/18: find out what's used for
	@GET
	@Secured
	@Path("/{acronym}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getByAcronym(@PathParam("acronym") String acronym) {
		return new Response().buildSuccess(surveyFacade.findByAcronym(acronym)).toSurveyJson();
	}

	@DELETE
	@Secured
	@Path("/{acronym}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteByAcronym(@PathParam("acronym") String acronym) {
		return new Response().buildSuccess(surveyFacade.deleteLastVersionByAcronym(acronym)).toJson();
	}

	@PUT
	@Secured
	@Path("/{acronym}/type")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateSurveyFormType(@PathParam("acronym") String acronym, UpdateSurveyFormTypeDto updateSurveyFormTypeDto) {
		updateSurveyFormTypeDto.acronym = acronym;
		return new Response().buildSuccess(surveyFacade.updateSurveyFormType(updateSurveyFormTypeDto)).toJson();
	}

}
