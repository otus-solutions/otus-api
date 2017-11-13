package br.org.otus.survey.activity;

import br.org.otus.security.Secured;
import org.restlet.resource.Post;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("activities/category")
public class CategoryResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(){
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String edit(){
        return null;
    }

    @GET
    @Path("/list")
//    @Secured
    public String getAll(){
        return null;
    }






}
