package br.org.otus.configuration.survey;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.survey.api.SurveyFacade;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("configuration/surveys")
public class SurveyResource {

	@Inject
	private SurveyFacade surveyFacade;

	@Inject
	private SecurityContext securityContext;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPermittedUndiscarded(@Context HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
		return new Response().buildSuccess(surveyFacade.listUndiscarded(userEmail)).toSurveyJson();
	}

	@GET
	@Secured
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllUndiscarded(@Context HttpServletRequest request) {
		return new Response().buildSuccess(surveyFacade.listAllUndiscarded()).toSurveyJson();
	}

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
	public String deleteLastVersionSurveyByAcronym(@PathParam("acronym") String acronym) {
		return new Response().buildSuccess(surveyFacade.deleteLastVersionByAcronym(acronym)).toJson();
	}

	@PUT
	@Secured
	@Path("/{acronym}/type")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateLastSurveySurveyType(@PathParam("acronym") String acronym, UpdateSurveyFormTypeDto updateSurveyFormTypeDto) {
		updateSurveyFormTypeDto.acronym = acronym;
		return new Response().buildSuccess(surveyFacade.updateLastVersionSurveyType(updateSurveyFormTypeDto)).toJson();
	}

	@GET
	@Secured
	@Path("/{acronym}/versions")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSurveyVersions(@PathParam("acronym") String acronym) {
		return new Response().buildSuccess(surveyFacade.listVersions(acronym)).toJson();
	}

  @PUT
  @Secured
  @Path("/update-required-external-id/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateSurveyRequiredExternalID(@PathParam("id") String surveyId, String requiredExternalId) {
    return new Response().buildSuccess(surveyFacade.updateSurveyRequiredExternalID(surveyId, requiredExternalId)).toJson();
  }
}
