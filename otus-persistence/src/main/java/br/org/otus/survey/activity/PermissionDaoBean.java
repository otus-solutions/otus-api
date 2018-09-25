package br.org.otus.survey.activity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.permission.Permission;
import org.ccem.otus.persistence.PermissionDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.mongodb.MongoGenericDao;

@Stateless
class PermissionDaoBean extends MongoGenericDao<Document> implements PermissionDao{
  
  private static final String COLLECTION_NAME = "permission";

  public PermissionDaoBean() {
    super(COLLECTION_NAME, Document.class);
}

  @Override
  public List<Permission> find() {
    
    List<Permission> permissions = new ArrayList<Permission>();
    
    FindIterable<Document> result = collection.find();
    
    result.forEach((Block<Document>) document -> {
      System.out.println(document);
      
  });
    
    return permissions;
  }
  
}