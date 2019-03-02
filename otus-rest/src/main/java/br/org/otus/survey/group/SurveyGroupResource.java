package br.org.otus.survey.group;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import br.org.otus.security.context.SecurityContext;

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
    public String addNewGroup(String surveyGroupJson) {
        String objectID = surveyGroupFacade.addNewGroup(surveyGroupJson);
        return new Response().buildSuccess(objectID).toJson();
    }

    @PUT
    @Secured
    @Path("/update-group")
    public String updateSurveyGroupAcronyms(String surveyGroupJson) {
        return new Response().buildSuccess(" modifiedCount: "+ surveyGroupFacade.updateGroupSurveyAcronyms(surveyGroupJson)).toJson();
    }

    @PUT
    @Secured
    @Path("/update-group-name/{old}/{new}")
    public String updateSurveyGroupName(@PathParam("old") String oldName, @PathParam("new") String newName) {
        return new Response().buildSuccess(" modifiedCount: "+ surveyGroupFacade.updateSurveyGroupName(oldName, newName)).toJson();
    }

    @DELETE
    @Secured
    @Path("delete-group/{name}")
    public String deleteGroup(@PathParam("name") String surveyGroupName){
        surveyGroupFacade.deleteGroup(surveyGroupName);
        return new Response().buildSuccess().toJson();
    }

    @GET
    @Secured
    @Path("groups-by-user")
    @Produces(MediaType.APPLICATION_JSON)
    public  String getSurveyGroupsByUser(@Context HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
        return new Response().buildSuccess(surveyGroupFacade.getSurveyGroupsByUser(userEmail)).toSurveyJson();
    }
}