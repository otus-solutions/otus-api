package service;

import model.Stage;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import persistence.StageDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class StageServiceBean implements StageService {

  @Inject
  private StageDao stageDao;


  @Override
  public ObjectId create(Stage stage) throws AlreadyExistException {
    return stageDao.create(stage);
  }

  @Override
  public void update(Stage stage) throws DataNotFoundException, AlreadyExistException {
    stageDao.update(stage);
  }

  @Override
  public void delete(ObjectId stageOID) throws DataNotFoundException {
    stageDao.delete(stageOID);
  }

  @Override
  public Stage getByID(ObjectId stageOID) throws DataNotFoundException {
    return stageDao.getByID(stageOID);
  }

  @Override
  public List<Stage> getAll() throws MemoryExcededException {
    return stageDao.getAll();
  }

  @Override
  public List<String> getAvailableSurveysOfStage(ObjectId stageOID) throws DataNotFoundException {
    return stageDao.getAvailableSurveysOfStage(stageOID);
  }

  @Override
  public void addAvailableSurveyInStage(ObjectId stageOID, List<String> newAcronyms) throws DataNotFoundException {
    stageDao.addAvailableSurveyInStage(stageOID, newAcronyms);
  }

  @Override
  public void removeAvailableSurveyInStage(ObjectId stageOID, List<String> newAcronyms) throws DataNotFoundException {
    stageDao.removeAvailableSurveyInStage(stageOID, newAcronyms);
  }
}
