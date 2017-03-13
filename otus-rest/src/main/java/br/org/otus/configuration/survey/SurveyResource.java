package br.org.otus.configuration.survey;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import br.org.otus.survey.api.SurveyFacade;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("configuration/surveys")
public class SurveyResource {

    @Inject
    private SurveyFacade surveyFacade;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return new Response().buildSuccess(surveyFacade.list()).toSurveyJson();
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
    public String deleteByAcronym(@PathParam("acronym") String acronym) {
    	return new Response().buildSuccess(surveyFacade.deleteByAcronym(acronym)).toJson();
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
