package br.org.otus.survey.activity;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.org.otus.survey.activity.builder.ActivityAccessPermissionQueryBuilder;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityAccessPermissionDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.mongodb.MongoGenericDao;

@Stateless
public class ActivityAccessPermissionDaoBean extends MongoGenericDao<Document> implements ActivityAccessPermissionDao {
  private static final String COLLECTION_NAME = "activity_permission";

  public ActivityAccessPermissionDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(ActivityAccessPermission activityAccessPermission) {
    Document parsed = Document.parse(ActivityAccessPermission.serialize(activityAccessPermission));
    super.persist(parsed);
  }

  @Override
  public void update(ActivityAccessPermission activityAccessPermission) {
    Document parsed = Document.parse(ActivityAccessPermission.serialize(activityAccessPermission));
    collection.updateOne(
      eq("_id", activityAccessPermission.getId()),
      new Document("$set", new Document("exclusiveDisjunction", activityAccessPermission.getExclusiveDisjunction())));
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
  public ActivityAccessPermission get(String acronym, Integer version) throws DataNotFoundException {
    ActivityAccessPermissionQueryBuilder queryBuilder = new ActivityAccessPermissionQueryBuilder();

    AggregateIterable<Document> results = collection.aggregate(queryBuilder.getByAcronymVersion(acronym, version));

    if (results == null || results.first() == null) {
      throw new DataNotFoundException("There are no results");
    }

    return ActivityAccessPermission.deserialize(results.first().toJson());
  }
}