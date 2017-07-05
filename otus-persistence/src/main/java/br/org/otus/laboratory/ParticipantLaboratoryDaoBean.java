package br.org.otus.laboratory;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.nin;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.dto.UpdateTubeAliquotsDTO;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;

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

	// findDocumentWithAliquotCodeNotInRecruimentNumber
	public Document findDocumentWithAliquotCodeNotInRecruimentNumber(long rn, String aliquotCode) throws DataNotFoundException {
		Document first = collection.find(and(in("tubes.aliquots.code", aliquotCode), nin("recruitmentNumber", rn))).first();
		if (first == null) {
			throw new DataNotFoundException();
		}
		return first;

	}

}
