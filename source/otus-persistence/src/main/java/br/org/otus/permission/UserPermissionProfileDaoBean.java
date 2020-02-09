package br.org.otus.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionProfileDao;

import com.google.gson.GsonBuilder;

import br.org.mongodb.MongoGenericDao;

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
    Document result = collection.find(new Document("name", profileName)).first();

    if (result == null) {
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

    if (first != null) {
      return SurveyGroupPermission.deserialize(first.toJson());
    } else {
      return null;
    }
  }

  @Override
  public void removeFromPermissionsProfile(String surveyGroupName) throws DataNotFoundException {
    UserPermissionDTO fullDTO = this.getProfile("DEFAULT");
    List<Permission> surveyGroupPermissionList = fullDTO.getPermissions()
      .stream()
      .filter(permission -> permission.getObjectType().equals("SurveyGroupPermission"))
      .collect(Collectors.toList());
    SurveyGroupPermission surveyGroupPermission = (SurveyGroupPermission) surveyGroupPermissionList.get(0);
    surveyGroupPermission.getGroups().remove(surveyGroupName);
    UserPermissionDTO newProfile = new UserPermissionDTO();
    newProfile.addPermission(surveyGroupPermission);
    fullDTO.concatenatePermissions(newProfile);
    Document parsed = Document.parse(UserPermissionDTO.serialize(fullDTO));
    Document update = new Document("$set", new Document("permissions", parsed.get("permissions")));
    collection.updateOne(new Document("name", "DEFAULT"), update);
  }
}
