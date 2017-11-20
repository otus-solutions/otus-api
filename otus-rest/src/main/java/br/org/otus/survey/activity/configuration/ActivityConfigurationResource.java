package br.org.otus.survey.activity.configuration;

import br.org.otus.rest.Response;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("activities/configuration")
public class ActivityConfigurationResource {

    @Inject
    ActivityCategoryFacade activityCategoryFacade;

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCategories(){
        return new Response().buildSuccess(activityCategoryFacade.list()).toJson();
    }

    @GET
    @Path("/categories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String name){
        return new Response().buildSuccess(activityCategoryFacade.getByName(name)).toJson();
    }

    @POST
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String create(String categoryLabel){
        return new Response().buildSuccess(activityCategoryFacade.create(categoryLabel)).toJson();
    }

    @DELETE
    @Path("/categories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String name){
        activityCategoryFacade.delete(name);
        return new Response().buildSuccess().toJson();
    }

    @PUT
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String update(String activityCategory){
        activityCategoryFacade.update(activityCategory);
        return new Response().buildSuccess().toJson();
    }

    @PUT
    @Path("/categories/default/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String setDefault(@PathParam("id") String name){
        activityCategoryFacade.setDefaultCategory(name);
        return new Response().buildSuccess().toJson();
    }
}
