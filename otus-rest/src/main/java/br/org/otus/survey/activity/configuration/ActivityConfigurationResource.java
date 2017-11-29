package br.org.otus.survey.activity.configuration;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("activities/configuration")
public class ActivityConfigurationResource {

    @Inject
    ActivityCategoryFacade activityCategoryFacade;

    @GET
    @Secured
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCategories(){
        return new Response().buildSuccess(activityCategoryFacade.list()).toJson();
    }

    @GET
    @Secured
    @Path("/categories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String name){
        return new Response().buildSuccess(activityCategoryFacade.getByName(name)).toJson();
    }

    @POST
    @Secured
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(String categoryLabel){
        return new Response().buildSuccess(activityCategoryFacade.create(categoryLabel)).toJson();
    }

    @DELETE
    @Secured
    @Path("/categories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String name){
        activityCategoryFacade.delete(name);
        return new Response().buildSuccess().toJson();
    }

    @PUT
    @Secured
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String update(String activityCategory){
        activityCategoryFacade.update(activityCategory);
        return new Response().buildSuccess().toJson();
    }

    @PUT
    @Secured
    @Path("/categories/default/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String setDefault(@PathParam("id") String name){
        activityCategoryFacade.setDefaultCategory(name);
        return new Response().buildSuccess().toJson();
    }
}
