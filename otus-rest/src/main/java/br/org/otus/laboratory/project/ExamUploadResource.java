package br.org.otus.laboratory.project;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.api.ExamUploadFacade;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;

@Path("laboratory-project/exam")
public class ExamUploadResource {

    @Inject
    private ExamUploadFacade examUploadFacade;

    @Inject
    private SecurityContext securityContext;

    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public String list(){
        return new Response().buildSuccess(examUploadFacade.list()).toCustomJson(ExamUploadDTO.getGsonBuilder());
    }

    @POST
    @Produces (MediaType.APPLICATION_JSON)
    @Consumes (MediaType.APPLICATION_JSON)
    public String create(@Context HttpServletRequest request, String examUploadJson){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
        return new Response().buildSuccess(examUploadFacade.create(examUploadJson, userEmail)).toJson();
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
        return new Response().buildSuccess(examUploadFacade.listResults(examId)).toCustomJson(ExamUploadDTO.getGsonBuilder());
    }

}
