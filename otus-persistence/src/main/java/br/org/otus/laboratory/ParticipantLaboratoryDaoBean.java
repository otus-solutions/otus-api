package br.org.otus.laboratory;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.collect.aliquot.Aliquot;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

public class ParticipantLaboratoryDaoBean extends MongoGenericDao<Document> implements ParticipantLaboratoryDao {
	private static final String COLLECTION_NAME = "participant_laboratory";

	public ParticipantLaboratoryDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public void persist(ParticipantLaboratory laboratoryParticipant) {
		super.persist(ParticipantLaboratory.serialize(laboratoryParticipant));
	}

	@Override
	public ParticipantLaboratory find() {
		Document document = super.findFirst();

		return ParticipantLaboratory.deserialize(document.toJson());
	}

	@Override
	public ParticipantLaboratory findByRecruitmentNumber(long rn) throws DataNotFoundException {
		Document result = collection.find(eq("recruitmentNumber", rn)).first();
		if (result == null) {
			throw new DataNotFoundException(new Throwable("Laboratory of Participant recruitment number: " + rn + " not found."));
		}
		return ParticipantLaboratory.deserialize(result.toJson());
	}

	@Override
	public ParticipantLaboratory updateLaboratoryData(ParticipantLaboratory labParticipant) throws DataNotFoundException {
		Document parsed = Document.parse(ParticipantLaboratory.serialize(labParticipant));
		parsed.remove("_id");

		UpdateResult updateLabData = collection.updateOne(eq("recruitmentNumber", labParticipant.getRecruitmentNumber()), new Document("$set", parsed),
				new UpdateOptions().upsert(false));

		if (updateLabData.getMatchedCount() == 0) {
			throw new DataNotFoundException(new Throwable("Laboratory of Participant recruitment number: " + labParticipant.getRecruitmentNumber()
					+ " does not exists."));
		}

		return labParticipant;
	}

	//TODO:
	public ParticipantLaboratory updateTubeOnParticipanteLaboratory(ParticipantLaboratory partipantLaboratory, String tubeCode, List<Aliquot> newAliquots) throws DataNotFoundException {
		UpdateResult updateResult = collection.updateOne(eq("tubes.code", tubeCode), new Document("$addToSet", new Document("tubes.$.aliquotes", newAliquots)));
		
		if (updateResult.getMatchedCount() == 0) {
			throw new DataNotFoundException(new Throwable("Tube Code not Found: " + tubeCode));
		}
		
		if(updateResult.getModifiedCount() > 0) {
			System.out.println(updateResult);
		}
		return null;
	}

	public Document findDocumentByAliquotCode(String aliquotCode) throws DataNotFoundException {
		Document first = collection.find(eq("tubes.aliquotes.code", aliquotCode)).first();
		if (first == null) {
			throw new DataNotFoundException();
		}
		return first;

	}

}
