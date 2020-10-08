package br.org.otus.configuration.api;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import model.Stage;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.service.ActivityService;
import service.StageService;

import javax.inject.Inject;
import java.util.List;

public class StageFacade {

  @Inject
  private StageService stageService;

  @Inject
  private ActivityService activityService;


  public String create(String stageJson) {
    try {
      return stageService.create(Stage.deserialize(stageJson)).toString();
    } catch (AlreadyExistException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void update(String stageID, String stageJson) {
    try{
      Stage stage = Stage.deserialize(stageJson);
      stage.setId(new ObjectId(stageID));
      stageService.update(stage);
    }
    catch (DataNotFoundException | AlreadyExistException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void delete(String stageID) {
    try{
      ObjectId stageOID = new ObjectId(stageID);
      stageService.delete(stageOID);
      activityService.removeStageFromActivities(stageOID);
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public Stage getByID(String stageID) {
    try{
      return stageService.getByID(new ObjectId(stageID));
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<Stage> getAll() {
    try{
      return stageService.getAll();
    }
    catch (MemoryExcededException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }
}
