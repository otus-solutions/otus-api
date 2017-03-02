package br.org.otus.participant;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.Participant;
import org.ccem.otus.persistence.FieldCenterDao;
import org.ccem.otus.persistence.ParticipantDao;

import com.mongodb.client.MongoCollection;

import br.org.mongodb.MongoGenericDao;

public class ParticipantDaoBean extends MongoGenericDao implements ParticipantDao {

	private static final String COLLECTION_NAME = "Participant";

	@Inject
	private FieldCenterDao fieldCenterDao;

	private MongoCollection<Participant> participantCollection;

	public ParticipantDaoBean() {
		super(COLLECTION_NAME);
	}

	@PostConstruct
	private void setuUp() {
		participantCollection = db.getCollection(COLLECTION_NAME, Participant.class);
	}

	@Override
	public void persist(Participant participant) {
		participantCollection.insertOne(participant);
	}

	@Override
	public ArrayList<Participant> find() {
		Map<String, FieldCenter> fieldCenterMap = getAllFieldCentes();

		ArrayList<Participant> participants = participantCollection.find().into(new ArrayList<Participant>());
		for (Participant participant : participants) {
			String acronym = participant.getFieldCenter().getAcronym();
			participant.setFieldCenter(fieldCenterMap.get(acronym));
		}

		return participants;
	}

	private Map<String, FieldCenter> getAllFieldCentes() {
		ArrayList<FieldCenter> fieldCenters = fieldCenterDao.find();
		Map<String, FieldCenter> fieldCenterMap = new HashMap<String, FieldCenter>();
		for (FieldCenter fieldCenter : fieldCenters) {
			fieldCenterMap.put(fieldCenter.getAcronym(), fieldCenter);
		}
		return fieldCenterMap;
	}

	@Override
	public Participant findByRecruitmentNumber(long rn) throws DataNotFoundException {
		Map<String, FieldCenter> fieldCenterMap = getAllFieldCentes();
		
		Participant result = participantCollection.find(eq("recruitmentNumber", rn)).first();
		
		if (result == null) {
			throw new DataNotFoundException(
					new Throwable("Participant with recruitment number {" + rn + "} not found."));
		}
		
		String acronym = result.getFieldCenter().getAcronym();
		result.setFieldCenter(fieldCenterMap.get(acronym));
		return result;
	}
}
