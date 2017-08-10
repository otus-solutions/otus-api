package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TransportationLotDaoBean extends MongoGenericDao<Document> implements TransportationLotDao {
	private static final String COLLECTION_NAME = "transportation_lot";

	public TransportationLotDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public void persist(TransportationLot transportationLot) {
		super.persist(TransportationLot.serialize(transportationLot));
	}

	@Override
	public TransportationLot update(TransportationLot transportationLot) {
		UpdateResult updateOne = collection.updateOne(eq("code", transportationLot.getCode()),
				new Document("$set", transportationLot), new UpdateOptions().upsert(false));
		if (updateOne.getMatchedCount() == 0) {
			// throw
		}

		return transportationLot;
	}

	@Override
	public List<TransportationLot> find() {
		ArrayList<TransportationLot> transportationLots = new ArrayList<TransportationLot>();

		FindIterable<Document> result = collection.find();
		result.forEach((Block<Document>) document -> {
			transportationLots.add(TransportationLot.deserialize(document.toJson()));
		});
		return transportationLots;
	}

	@Override
	public void delete(String id) {
		DeleteResult deleteResult = collection.deleteOne(eq("code", id));
		if (deleteResult.getDeletedCount() == 0) {
			// throw
		}
	}

}
