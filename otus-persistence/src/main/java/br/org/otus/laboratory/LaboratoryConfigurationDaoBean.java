package br.org.otus.laboratory;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import br.org.mongodb.MongoGenericDao;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

public class LaboratoryConfigurationDaoBean extends MongoGenericDao implements LaboratoryConfigurationDao {

	private static final String COLLECTION_NAME = "laboratory_configuration";

	public LaboratoryConfigurationDaoBean() {
		super(COLLECTION_NAME);
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

		UpdateResult updatedData = collection.updateOne(eq("_id", configuration.getId()), new Document("$set", parsed), new UpdateOptions().upsert(false));

		if (updatedData.getModifiedCount() == 0) {
			throw new Exception("Update was not executed.");
		}
	}

}
