package br.org.otus.stage;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.Stage;
import br.org.otus.persistence.StageDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class StageDaoBean extends MongoGenericDao<Document> implements StageDao {

  private static final String COLLECTION_NAME = "stage";
  private static final String ACTIVITY_COLLECTION_NAME = "activity";

  public StageDaoBean(){
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ObjectId create(Stage stage) {
    Document parsed = Document.parse(Stage.serialize(stage));
    collection.insertOne(parsed);
    return parsed.getObjectId(ID_FIELD_NAME);
  }

  @Override
  public void update(Stage stage) throws DataNotFoundException {
    UpdateResult updateResult = collection.updateOne(
      eq(ID_FIELD_NAME, stage.getId()),
        new Document("$set", new Document("name", stage.getName()))
    );

    if(updateResult.getMatchedCount() == 0){
      throw new DataNotFoundException(new Throwable("Stage with id " + stage.getId().toString() + " was not found."));
    }
  }

  @Override
  public void delete(ObjectId stageOID) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq(ID_FIELD_NAME, stageOID));
    if(deleteResult.getDeletedCount() == 0){
      throw new DataNotFoundException(new Throwable("Stage with id " + stageOID.toString() + " was not found."));
    }
  }

  @Override
  public Stage getByID(ObjectId stageOID) throws DataNotFoundException {
    Document result = collection.find(eq(ID_FIELD_NAME, stageOID)).first();
    if(result == null){
      throw new DataNotFoundException(new Throwable("Stage with id " + stageOID.toString() + " was not found."));
    }
    return Stage.deserialize(result.toJson());
  }

  @Override
  public List<Stage> getAll() throws MemoryExcededException {
    List<Stage> stages = new ArrayList<>();

    FindIterable<Document> results = collection.find();
    MongoCursor<Document> iterator = results.iterator();

    while(iterator.hasNext()){
      try{
        Document document = iterator.next();
        stages.add(Stage.deserialize(document.toJson()));
      }
      catch (OutOfMemoryError e){
        stages.clear();
        throw new MemoryExcededException("Stages extraction exceeded memory used.");
      }
    }

    return stages;
  }
}