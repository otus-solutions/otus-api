package br.org.otus.laboratory.permission;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.permission.LaboratoryAccessPermission;
import br.org.otus.laboratory.configuration.permission.LaboratoryAccessPermissionDao;

public class LaboratoryActivityAccessPermissionDaoBean extends MongoGenericDao<Document> implements LaboratoryAccessPermissionDao {
  private static final String COLLECTION_NAME = "laboratory_permission";

  public LaboratoryActivityAccessPermissionDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }
  
  @Override
  public List<LaboratoryAccessPermission> find() {
    List<LaboratoryAccessPermission> permissions = new ArrayList<LaboratoryAccessPermission>();

    FindIterable<Document> result = collection.find();

    result.forEach((Block<Document>) document -> {
      permissions.add(LaboratoryAccessPermission.deserialize(document.toJson()));
    });

    return permissions;
  }

  @Override
  public void persist(LaboratoryAccessPermission laboratoryAccessPermission) {
    Document parsed = Document.parse(LaboratoryAccessPermission.serialize(laboratoryAccessPermission));
    super.persist(parsed);
  }

  @Override
  public void update(LaboratoryAccessPermission laboratoryAccessPermission) {
    // TODO: O laboratorio precisa utilizar um exclusiveDisjunction?
    Document parsed = Document.parse(LaboratoryAccessPermission.serialize(laboratoryAccessPermission));
    // super.collection.updateOne(eq("_id", laboratoryAccessPermission.getId()), new Document("$set", new Document("exclusiveDisjunction", laboratoryAccessPermission.getExclusiveDisjunction())));
  }

}
