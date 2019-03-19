package br.org.otus.permission;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionProfileDao;

import br.org.mongodb.MongoGenericDao;

import java.util.ArrayList;

public class UserPermissionProfileDaoBean extends MongoGenericDao<Document> implements UserPermissionProfileDao {
  private static final String COLLECTION_NAME = "user_permission_profile";

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }

  public UserPermissionProfileDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public UserPermissionDTO getProfile(String profileName) throws DataNotFoundException {
    Document result = collection.find(new Document("name",profileName)).first();
    
    if(result == null) {
      throw new DataNotFoundException(new Throwable("Permission profile: {" + profileName + "} not found."));
    }
    
    return UserPermissionDTO.deserialize(result.toJson());
  }

  @Override
  public SurveyGroupPermission getGroupPermission(String profileName) {
    ArrayList<Bson> pipeline = new ArrayList<>();
    pipeline.add(parseQuery("{$match:{\"name\":\"DEFAULT\"}}"));
    pipeline.add(parseQuery("{$unwind:\"$permissions\"}"));
    pipeline.add(parseQuery("{$match:{\"permissions.objectType\":\"SurveyGroupPermission\"}}"));
    pipeline.add(parseQuery("{$replaceRoot:{newRoot:\"$permissions\"}}"));

    Document first = collection.aggregate(pipeline).first();

    if (first != null){
      return SurveyGroupPermission.deserialize(first.toJson());
    } else {
      return null;
    }
  }
}
