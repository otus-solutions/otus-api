package persistence;


import model.Stage;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

import java.util.List;

public interface StageDao {

  ObjectId create(Stage stage);

  void update(Stage stage) throws DataNotFoundException;

  void delete(ObjectId stageOID) throws DataNotFoundException;

  Stage getByID(ObjectId stageOID) throws DataNotFoundException;

  List<Stage> getAll() throws MemoryExcededException;
}
