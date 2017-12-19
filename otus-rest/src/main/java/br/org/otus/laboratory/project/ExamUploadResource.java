package br.org.otus.laboratory.project;

import br.org.otus.laboratory.project.api.ExamUploadFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;

@Path("laboratory-project/exam")
public class ExamUploadResource {

    @Inject
    private ExamUploadFacade examUploadFacade;

    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public String list(){
        return new Response().buildSuccess(examUploadFacade.list()).toSurveyJson();
    }

    @POST
    @Produces (MediaType.APPLICATION_JSON)
    @Consumes (MediaType.APPLICATION_JSON)
    public String create(String examUploadJson){
        return new Response().buildSuccess(examUploadFacade.create(examUploadJson)).toJson();
    }

    @DELETE
    @Produces (MediaType.APPLICATION_JSON)
    public String delete(String id){
        //TODO 18/12/17: check params. will we send oid through url?
        examUploadFacade.deleteById(id);
        return new Response().buildSuccess().toJson();
    }

    @GET
    @Path("/results/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    @Consumes (MediaType.APPLICATION_JSON)
    public String getResults(@PathParam("id") String examId){
        return new Response().buildSuccess(examUploadFacade.listResults(examId)).toJson();
    }

}
