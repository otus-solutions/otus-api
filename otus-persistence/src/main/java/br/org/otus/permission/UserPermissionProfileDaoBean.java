package br.org.otus.permission;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionProfileDao;

import br.org.mongodb.MongoGenericDao;

public class UserPermissionProfileDaoBean extends MongoGenericDao<Document> implements UserPermissionProfileDao {
  private static final String COLLECTION_NAME = "user_permission_profile";

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
}
