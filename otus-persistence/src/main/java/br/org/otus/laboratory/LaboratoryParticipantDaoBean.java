package br.org.otus.laboratory;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.participant.LaboratoryParticipant;
import br.org.otus.laboratory.participant.Tube;
import br.org.otus.laboratory.persistence.LaboratoryParticipantDao;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

@Stateless
public class LaboratoryParticipantDaoBean extends MongoGenericDao implements LaboratoryParticipantDao {
	private static final String COLLECTION_NAME = "LaboratoryParticipant";

	@Inject
	private LaboratoryParticipantDao lab;

	public LaboratoryParticipantDaoBean() {
		super(COLLECTION_NAME);
	}

	@Override
	public void persist(LaboratoryParticipant laboratoryParticipant) {
		super.persist(LaboratoryParticipant.serialize(laboratoryParticipant));
	}

	@Override
	public LaboratoryParticipant find() {
		Document document = super.findFirst();

		return LaboratoryParticipant.deserialize(document.toJson());
	}

	@Override
	public Tube findByCode(Integer code) {
		return lab.findByCode(code);
	}

	@Override
	public LaboratoryParticipant findByRecruitmentNumber(long rn) throws DataNotFoundException{
		Document result = collection.find(eq("recruitmentNumber", rn)).first();
		if(result == null) {
			throw new DataNotFoundException(new Throwable("Laboratory of Participant recruitment number: " + rn + " not found."));
		}
		return LaboratoryParticipant.deserialize(result.toJson());
	}

	@Override
	public LaboratoryParticipant updateLaboratoryData(LaboratoryParticipant labParticipant) throws DataNotFoundException {
		Document parsed = Document.parse(LaboratoryParticipant.serialize(labParticipant));
		parsed.remove("_id");
		
		UpdateResult updateLabData = collection.updateOne(eq("recruitmentNumber", labParticipant.getRecruitmentNumber()),
		    new Document("$set", parsed), new UpdateOptions().upsert(false));

		if (updateLabData.getModifiedCount() == 0) {
			throw new DataNotFoundException();
		}

		return labParticipant;
	}
	

}
