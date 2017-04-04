package br.org.otus.datasource;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.persistence.DataSourceDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import br.org.mongodb.MongoGenericDao;

@Stateless
public class DataSourceDaoBean extends MongoGenericDao implements DataSourceDao {

	private static final String COLLECTION_NAME = "datasource";

	public DataSourceDaoBean() {
		super(COLLECTION_NAME);
	}

	@Override
	public void persist(DataSource dataSource) throws AlreadyExistException {
		if (!isAvailableID(dataSource.getId())) {
			throw new AlreadyExistException(new Throwable("This ID {" + dataSource.getId() + "} already exists."));
		}
		collection.insertOne(Document.parse(DataSource.serialize(dataSource)));
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

}
