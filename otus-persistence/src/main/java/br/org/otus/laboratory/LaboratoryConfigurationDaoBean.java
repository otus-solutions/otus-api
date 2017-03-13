package br.org.otus.laboratory;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.config.LaboratoryConfiguration;
import br.org.otus.laboratory.participant.LaboratoryParticipant;
import br.org.otus.laboratory.persistence.LaboratoryConfigurationDao;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import javax.ejb.Local;

import static com.mongodb.client.model.Filters.eq;

@Local(LaboratoryConfigurationDao.class)
public class LaboratoryConfigurationDaoBean extends MongoGenericDao implements LaboratoryConfigurationDao {

	private static final String COLLECTION_NAME = "laboratory_config";
	
	private LaboratoryConfiguration tubeConfiguration;

	public LaboratoryConfigurationDaoBean() {
		super(COLLECTION_NAME);
	}

	public void persist(LaboratoryConfiguration laboratoryConfiguration) {
		super.persist(LaboratoryConfiguration.serialize(laboratoryConfiguration));
	}

	@Override
	public LaboratoryConfiguration find() {
		Document document = super.findFirst();
		return LaboratoryConfiguration.deserialize(document.toJson());
	}

	@Override
	public LaboratoryParticipant update(LaboratoryConfiguration configuration) {
		Document parsed = Document.parse(LaboratoryConfiguration.serialize(configuration));
		parsed.remove("_id");

		UpdateResult updatedData = collection.updateOne(eq("_id", configuration.getId()), new Document("$set", parsed),
		    new UpdateOptions().upsert(false));

		if (updatedData.getModifiedCount() == 0) {
			System.out.println("Lança alguma excessão");
		}

		return null;
	}

}
