package br.org.otus.service;

import br.org.otus.model.Stage;
import br.org.otus.persistence.StageDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class StageServiceBean implements StageService {

  @Inject
  private StageDao stageDao;


  @Override
  public ObjectId create(Stage stage) {
    return stageDao.create(stage);
  }

  @Override
  public void update(Stage stage) throws DataNotFoundException {
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
}
