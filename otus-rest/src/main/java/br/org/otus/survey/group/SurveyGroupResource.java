package br.org.otus.survey.group;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;
import org.ccem.otus.model.survey.group.dto.SurveyGroupNameDto;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("survey")
public class SurveyGroupResource {

    @Inject
    private SurveyGroupFacade surveyGroupFacade;

    @Inject
    private SecurityContext securityContext;

    @GET
    @Secured
    @Path("/groups")
    @Produces(MediaType.APPLICATION_JSON)
    public String getListOfSurveyGroups() {
        return new Response().buildSuccess(surveyGroupFacade.getListOfSurveyGroups()).toSurveyJson();
    }

    @POST
    @Secured
    @Path("/new-group")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addNewSurveyGroup(String surveyGroupJson) {
        String objectID = surveyGroupFacade.addNewSurveyGroup(surveyGroupJson);
        return new Response().buildSuccess(objectID).toJson();
    }

    @PUT
    @Secured
    @Path("/update-group")
    public String updateSurveyGroupAcronyms(String surveyGroupJson) {
        return new Response().buildSuccess(" modifiedCount: " + surveyGroupFacade.updateSurveyGroupAcronyms(surveyGroupJson)).toJson();
    }

    @PUT
    @Secured
    @Path("/update-group-name")
    public String updateSurveyGroupName(SurveyGroupNameDto surveyGroupNameDto) {
        return new Response().buildSuccess(" modifiedCount: " + surveyGroupFacade.updateSurveyGroupName(surveyGroupNameDto)).toJson();
    }

    @POST
    @Secured
    @Path("/delete-group")
    public String deleteSurveyGroup(SurveyGroupNameDto surveyGroupNameDto) {
        surveyGroupFacade.deleteSurveyGroup(surveyGroupNameDto);
        return new Response().buildSuccess().toJson();
    }

    @GET
    @Secured
    @Path("/groups-by-user")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSurveyGroupsByUser(@Context HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
        return new Response().buildSuccess(surveyGroupFacade.getSurveyGroupsByUser(userEmail)).toSurveyJson();
    }
}