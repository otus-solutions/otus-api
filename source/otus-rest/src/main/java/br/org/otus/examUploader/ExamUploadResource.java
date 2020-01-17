package br.org.otus.examUploader;

import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
import br.org.otus.security.context.SecurityContext;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("exam-upload/")
public class ExamUploadResource {

    @Inject
    private ExamUploadFacade examUploadFacade;

    @Inject
    private SecurityContext securityContext;

    @GET
    @Secured
    @Produces (MediaType.APPLICATION_JSON)
    public String list(){
        return new Response().buildSuccess(examUploadFacade.list()).toJson(ExamUploadDTO.getGsonBuilder());
    }

    @GET
    @Secured
    @Path("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public String getById(@PathParam("id") String id){
        return new Response().buildSuccess(examUploadFacade.getById(id)).toJson(ExamUploadDTO.getGsonBuilder());
    }

    @POST
    @Secured
    @Produces (MediaType.APPLICATION_JSON)
    @Consumes (MediaType.APPLICATION_JSON)
    public String create(@Context HttpServletRequest request, String examUploadJson){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
        return new Response().buildSuccess(examUploadFacade.create(examUploadJson, userEmail)).toJson();
    }

    @DELETE
    @Secured
    @Path("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String id){
        examUploadFacade.deleteById(id);
        return new Response().buildSuccess().toJson();
    }

    @GET
    @Secured
    @Path("/results/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public String getResults(@PathParam("id") String examLotId){
        return new Response().buildSuccess(examUploadFacade.listResults(examLotId)).toJson(ExamUploadDTO.getGsonBuilder());
    }

}
