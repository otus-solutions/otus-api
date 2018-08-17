package br.org.otus.security;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.MongoCommandException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PasswordResetControlDaoBean extends MongoGenericDao<Document> implements PasswordResetControlDao {

  private static final String COLLECTION_NAME = "password_reset_control";

  @PostConstruct
  private void setUp() {
    //TODO 17/08/18: pull this to proper service
    try {
      this.collection.createIndex(
        new Document("creationDate", 1),
        new IndexOptions().expireAfter(1L, TimeUnit.HOURS)
      );
    }catch (MongoCommandException ignored) {}


  }

  public PasswordResetControlDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void registerToken(String token, String email) {
    Document insertion = new Document();
    insertion.append("token", token);
    insertion.append("email", email);
    insertion.append("creationDate", new Date());
    collection.insertOne(insertion);
  }

  @Override
  public void removeRegister(String token) {
    Document query = new Document();
    query.append("token", token);
    DeleteResult deleteResult = collection.deleteOne(query);

    //TODO 17/08/18: treat this
    deleteResult.wasAcknowledged();

  }

  @Override
  public void removeAllRegisters(String email) {
    Document query = new Document();
    query.append("email", email);
    DeleteResult deleteResult = collection.deleteMany(query);

    //TODO 17/08/18: treat this
    deleteResult.wasAcknowledged();
  }

  @Override
  public boolean tokenExists(String token) {
    Document query = new Document();
    query.append("token", token);
    FindIterable<Document> found = collection.find(query);
    return found.first() != null;
  }

  @Override
  public boolean hasRegister(String email) {
    Document query = new Document();
    query.append("email", email);
    FindIterable<Document> found = collection.find(query);
    return found.first() != null;
  }
}
