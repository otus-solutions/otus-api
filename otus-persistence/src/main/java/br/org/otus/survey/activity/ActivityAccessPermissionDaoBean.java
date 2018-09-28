package br.org.otus.survey.activity;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityAccessPermissionDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.mongodb.MongoGenericDao;

@Stateless
class ActivityAccessPermissionDaoBean extends MongoGenericDao<Document> implements ActivityAccessPermissionDao {

  private static final String COLLECTION_NAME = "activity_access_permission";

  public ActivityAccessPermissionDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public List<ActivityAccessPermission> find() {

    List<ActivityAccessPermission> permissions = new ArrayList<ActivityAccessPermission>();

    FindIterable<Document> result = collection.find();

    result.forEach((Block<Document>) document -> {
      permissions.add(ActivityAccessPermission.deserialize(document.toJson()));
    });

    return permissions;
  }

  @Override
  public void persist(ActivityAccessPermission activityAccessPermission) {
    Document parsed = Document.parse(ActivityAccessPermission.serialize(activityAccessPermission));
    super.persist(parsed);
  }

  @Override
  public void update(ActivityAccessPermission activityAccessPermission) {
    Document parsed = Document.parse(ActivityAccessPermission.serialize(activityAccessPermission));
    super.collection.updateOne(eq("_id", activityAccessPermission.getId()), new Document("$set", parsed));
  }
}