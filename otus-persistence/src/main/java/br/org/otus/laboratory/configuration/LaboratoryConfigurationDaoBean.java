package br.org.otus.laboratory.configuration;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

import org.bson.Document;

import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;

public class LaboratoryConfigurationDaoBean extends MongoGenericDao<Document> implements LaboratoryConfigurationDao {

	private static final String COLLECTION_NAME = "laboratory_configuration";

	public LaboratoryConfigurationDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public LaboratoryConfiguration find() {
		Document document = super.findFirst();

		return LaboratoryConfiguration.deserialize(document.toJson());
	}

	public void persist(LaboratoryConfiguration laboratoryConfiguration) {
		super.persist(LaboratoryConfiguration.serialize(laboratoryConfiguration));
	}

	@Override
	public void update(LaboratoryConfiguration configuration) throws Exception {
		Document parsed = Document.parse(LaboratoryConfiguration.serialize(configuration));
		parsed.remove("_id");

		UpdateResult updatedData = collection.updateOne(eq("_id", configuration.getId()), new Document("$set", parsed),
				new UpdateOptions().upsert(false));

		if (updatedData.getModifiedCount() == 0) {
			throw new Exception("Update was not executed.");
		}
	}
	
	public String createNewLotCode() {		
		Document updateLotCode = collection.findOneAndUpdate(exists("lotConfiguration.lastInsertion"), 
				new Document("$inc", new Document("lotConfiguration.lastInsertion", 1)),  
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		
		LaboratoryConfiguration laboratoryConfiguration = LaboratoryConfiguration.deserialize(updateLotCode.toJson());
		
		return laboratoryConfiguration.getLotConfiguration().getLastInsertion().toString();
	}
	
}
