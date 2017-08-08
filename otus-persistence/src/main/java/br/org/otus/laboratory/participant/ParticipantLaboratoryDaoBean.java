package br.org.otus.laboratory.participant;

import static com.mongodb.client.model.Filters.eq;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.project.transportation.TransportationLot;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;

import java.util.ArrayList;

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

	public Document findDocumentByAliquotCode(String aliquotCode) throws DataNotFoundException {
		Document first = collection.find(eq("tubes.aliquotes.code", aliquotCode)).first();
		if (first == null) {
			throw new DataNotFoundException();
		}
		return first;

	}

	@Override
	public ArrayList<Aliquot> getFullAliquotsList() {
	    ArrayList<Aliquot> fullList = new ArrayList<Aliquot>();

		FindIterable<Document> list = collection.find();
		list.forEach((Block<Document>) document ->{
            ParticipantLaboratory laboratory = ParticipantLaboratory.deserialize(document.toJson());
            fullList.addAll(laboratory.getAliquotsList());
        });

		return fullList;
	}

	@Override
	public ArrayList<ParticipantLaboratory> getAllParticipantLaboratory() {
		FindIterable<Document> result = super.list();
		ArrayList<ParticipantLaboratory> participantList = new ArrayList<ParticipantLaboratory>();
		
		result.forEach((Block<Document>) document -> {
			participantList.add(ParticipantLaboratory.deserialize(document.toJson()));
		});
		return participantList;
	}

}
