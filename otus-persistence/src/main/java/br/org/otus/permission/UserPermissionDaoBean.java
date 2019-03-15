package br.org.otus.permission;

import java.util.Arrays;

import org.bson.Document;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionDao;

import br.org.mongodb.MongoGenericDao;

public class UserPermissionDaoBean extends MongoGenericDao<Document> implements UserPermissionDao {

  private static final String COLLECTION_NAME = "user_permission";

  public UserPermissionDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  public UserPermissionDTO getAll(String email) {
    UserPermissionDTO userPermissionDTO = new UserPermissionDTO();

    Document result = collection.aggregate(Arrays.asList(
        new Document("$match",
            new Document("email","boese.work@gmail.com")
            ),
        new Document("$group",
            new Document("_id",new Document())
            .append("permissions", 
                new Document("$push",new Document("objectType","$objectType")
                    .append("values", "$values")))))
        ).first();
    if (result == null) {
      userPermissionDTO = UserPermissionDTO.deserialize(result.toJson());
    }
    return userPermissionDTO;
  }

}
