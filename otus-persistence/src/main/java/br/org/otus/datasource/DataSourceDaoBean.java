package br.org.otus.datasource;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.persistence.DataSourceDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.mongodb.MongoGenericDao;
import org.ccem.otus.utils.DataSourceValuesMapping;

@Stateless
public class DataSourceDaoBean extends MongoGenericDao<Document> implements DataSourceDao {

  private static final String COLLECTION_NAME = "datasource";

  public DataSourceDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(DataSource dataSource) throws AlreadyExistException {
    if (!isAvailableID(dataSource.getId())) {
      throw new AlreadyExistException(new Throwable("This ID {" + dataSource.getId() + "} already exists."));
    }
    collection.insertOne(Document.parse(DataSource.serialize(dataSource)));
  }

  @Override
  public void update(DataSource dataSource) throws ValidationException {
    if (!isAvailableID(dataSource.getId())) {
      collection.updateOne(
        new Document("id", dataSource.getId()),
        new Document("$set", new Document("data", Document.parse(DataSource.serialize(dataSource)).get("data"))));

    } else {
      throw new ValidationException(new Throwable("This ID {" + dataSource.getId() + "} not exists."));
    }
  }

  @Override
  public List<DataSource> find() {
    ArrayList<DataSource> dataSources = new ArrayList<DataSource>();

    FindIterable<Document> result = collection.find();
    result.forEach((Block<Document>) document -> {
      dataSources.add(DataSource.deserialize(document.toJson()));
    });

    return dataSources;
  }

  @Override
  public DataSource findByID(String id) throws DataNotFoundException {
    Document result = collection.find(eq("id", id)).first();

    if (result == null) {
      throw new DataNotFoundException(new Throwable("ID {" + id + "} not found."));
    }

    return DataSource.deserialize(result.toJson());
  }

  public boolean isAvailableID(String id) {
    Document result = collection.find(eq("id", id)).first();
    return (result == null) ? true : false;
  }

  @Override
  public DataSourceValuesMapping getDataSourceMapping() {
    ArrayList<Bson> pipeline = new ArrayList<Bson>();
    DataSourceValuesMapping dataSourceValuesMapping = null;
    pipeline.add(parseQuery("{$unwind:\"$data\"}"));
    pipeline.add(parseQuery("{$group:{_id:{},valuesArray:{$push:{id:\"$id\",values:[\"$data.value\",\"$data.extractionValue\"]}}}}"));
    pipeline.add(parseQuery("{$unwind:\"$valuesArray\"}"));
    pipeline.add(parseQuery("{$group:{_id:\"$valuesArray.id\",hashMap:{$push:\"$valuesArray.values\"}}}"));
    pipeline.add(parseQuery("{$group:{_id:{},mappingList:{$push:{newRoot:[\"$_id\",{hashMap:\"$hashMap\"}]}}}}"));
    pipeline.add(parseQuery("{$unwind:\"$mappingList\"}"));
    pipeline.add(parseQuery("{$group:{_id:{},mappingList:{$push:\"$mappingList.newRoot\"}}}"));
    Document output = collection.aggregate(pipeline).first();
    if (output != null) {
      dataSourceValuesMapping = DataSourceValuesMapping.deserialize(output.toJson());
    }
    return dataSourceValuesMapping;
  }

  private Bson parseQuery(String stage) {
    return new GsonBuilder().create().fromJson(stage, Document.class);
  }

}
