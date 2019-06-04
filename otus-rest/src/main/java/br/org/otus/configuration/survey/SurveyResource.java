package br.org.otus.configuration.survey;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.survey.api.SurveyFacade;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;

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

	@GET
  @Secured
  @Path("/{acronym}/{version}")
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@PathParam("acronym") String acronym,@PathParam("version") Integer version) {
    return new Response().buildSuccess(surveyFacade.get(acronym, version)).toSurveyJson();
  }

}
