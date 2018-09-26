package br.org.otus.survey.activity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityAccessPermissionDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.mongodb.MongoGenericDao;

@Stateless
class ActivityAccessPermissionDaoBean extends MongoGenericDao<Document> implements ActivityAccessPermissionDao{
  
  private static final String COLLECTION_NAME = "permission";

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
  
}