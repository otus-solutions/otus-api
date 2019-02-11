package br.org.mongodb;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

public abstract class MongoGenericDao<T> {

  @Inject
  protected MongoDatabase db;
  private String collectionName;
  protected MongoCollection<T> collection;
  private Class<T> typedClass;

  @PostConstruct
  private void setUp() {
    collection = (MongoCollection<T>) db.getCollection(collectionName, typedClass);
  }

  public MongoGenericDao(String collectionName, Class<T> clazz) {
    this.collectionName = collectionName;
    this.typedClass = clazz;
  }

  public void persist(T document) {
    collection.insertOne(document);
  }

  public void persist(String document) {
    collection.insertOne((T) Document.parse(document));
  }

  public FindIterable<T> list() {
    return collection.find();
  }

  public long count() {
    return collection.count();
  }

  public T findFirst() {
    return list().first();
  }

  public AggregateIterable<T> aggregate(List<Bson> query) {
    return collection.aggregate(query).allowDiskUse(true);
  }

}
