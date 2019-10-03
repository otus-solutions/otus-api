package br.org.otus.permission;

import java.util.Arrays;

import org.bson.Document;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionDao;

import com.mongodb.client.model.UpdateOptions;

import br.org.mongodb.MongoGenericDao;

public class UserPermissionDaoBean extends MongoGenericDao<Document> implements UserPermissionDao {

  private static final String COLLECTION_NAME = "user_permission";

  public UserPermissionDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public UserPermissionDTO getAll(String email) {
    UserPermissionDTO userPermissionDTO = new UserPermissionDTO();

    Document result = collection.aggregate(Arrays.asList(
        new Document("$match", new Document("email",email)),
        new Document("$group", 
            new Document("_id", "")
            .append("permissions", 
                new Document("$push","$$ROOT")
                )
            )
        )).first();
    
    if (result != null) {
      userPermissionDTO = UserPermissionDTO.deserialize(result.toJson());
    }
    return userPermissionDTO;
  }

  @Override
  public void savePermission(Permission permission) {
    Document parsed = Document.parse(SurveyGroupPermission.serialize(permission));
    collection.updateOne(new Document("objectType", permission.getObjectType()).append("email",permission.getEmail()),new Document("$set", parsed),new UpdateOptions().upsert(true));
  }

  @Override
  public void deletePermission(Permission permission) {
    collection.deleteOne(new Document("objectType", permission.getObjectType()).append("email",permission.getEmail()));
  }

  @Override
  public SurveyGroupPermission getGroupPermission(String email) {
    Document first = collection.find(new Document("objectType", "SurveyGroupPermission").append("email", email)).first();
    if (first != null){
      return SurveyGroupPermission.deserialize(first.toJson());
    } else {
      return null;
    }
  }

  @Override
  public void removeFromPermissions(String surveyGroupName) {
    collection.updateMany(new Document("groups",surveyGroupName),new Document("$pull",new Document("groups", surveyGroupName)));
  }

}
