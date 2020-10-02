package br.org.otus.stage;

import br.org.otus.model.Stage;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;
import br.org.otus.service.StageService;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

import javax.inject.Inject;
import java.util.List;

public class StageFacade {

  @Inject
  private StageService stageService;

  public String create(String stageJson) {
    return stageService.create(Stage.deserialize(stageJson)).toString();
  }

  public void update(String stageJson) {
    Stage stage = Stage.deserialize(stageJson);
    if(stage.getId() == null){
      throw new HttpResponseException(Validation.build("Can not update stage without id"));
    }
    try{
      stageService.update(stage);
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void delete(String stageID) {
    try{
      stageService.delete(new ObjectId(stageID));

      // TODO update de todas as atividades da etapa

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

//  public String create(String stageJson) {
//    return null;
//  }
//
//  public void update(String stageJson) {
//
//  }
//
//  public void delete(String stageID) {
//
//  }
//
//  public Stage getByID(String stageID) {
//    return null;
//  }
//
//  public List<Stage> getAll() {
//    return null;
//  }
}
