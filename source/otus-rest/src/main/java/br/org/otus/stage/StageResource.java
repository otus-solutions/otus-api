package br.org.otus.stage;

import br.org.otus.configuration.api.StageFacade;
import br.org.otus.rest.Response;
import model.Stage;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("stage")
public class StageResource {

  @Inject
  private StageFacade stageFacade;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(String stageJson){
    String stageId = stageFacade.create(stageJson);
    return (new Response()).buildSuccess(stageId).toJson();
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String update(@PathParam("id") String stageID, String stageJson){
    stageFacade.update(stageID, stageJson);
    return (new Response()).buildSuccess().toJson();
  }

  @DELETE
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String delete(@PathParam("id") String stageID){
    stageFacade.delete(stageID);
    return (new Response()).buildSuccess().toJson();
  }

  @GET
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getByID(@PathParam("id") String stageID){
    Stage stage = stageFacade.getByID(stageID);
    return (new Response()).buildSuccess(stage).toJson(Stage.getFrontGsonBuilder());
  }

  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getAll(){
    List<Stage> stages = stageFacade.getAll();
    return (new Response()).buildSuccess(stages).toJson(Stage.getFrontGsonBuilder());
  }
}
