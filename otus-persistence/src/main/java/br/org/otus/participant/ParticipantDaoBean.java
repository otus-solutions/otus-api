package br.org.otus.participant;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;

import com.mongodb.client.MongoCollection;

import br.org.mongodb.MongoGenericDao;

public class ParticipantDaoBean extends MongoGenericDao implements ParticipantDao {

	private static final String COLLECTION_NAME = "participant";

	@Inject
	private FieldCenterDao fieldCenterDao;

	private MongoCollection<Participant> participantCollection;

	public ParticipantDaoBean() {
		super(COLLECTION_NAME);
	}

	@PostConstruct
	private void setUp() {
		participantCollection = db.getCollection(COLLECTION_NAME, Participant.class);
	}

	@Override
	public void persist(Participant participant) {
		participantCollection.insertOne(participant);
	}

	@Override
	public ArrayList<Participant> find() {
		Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();
		
		ArrayList<Participant> participants = participantCollection.find().into(new ArrayList<Participant>());
		for (Participant participant : participants) {
			String acronym = participant.getFieldCenter().getAcronym();
			participant.setFieldCenter(fieldCenterMap.get(acronym));
		}
		
		return participants;
	}
	
	@Override
	public ArrayList<Participant> findByFieldCenter(FieldCenter fieldCenter) {
		Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();

		ArrayList<Participant> participants = participantCollection.find(eq("fieldCenter.acronym", fieldCenter.getAcronym())).into(new ArrayList<Participant>());
		for (Participant participant : participants) {
			String acronym = participant.getFieldCenter().getAcronym();
			participant.setFieldCenter(fieldCenterMap.get(acronym));
		}

		return participants;
	}

	@Override
	public Participant findByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
		Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();

		Participant result = participantCollection.find(eq("recruitmentNumber", recruitmentNumber)).first();

		if (result == null) {
			throw new DataNotFoundException(
					new Throwable("Participant with recruitment number {" + recruitmentNumber + "} not found."));
		}

		String acronym = result.getFieldCenter().getAcronym();
		result.setFieldCenter(fieldCenterMap.get(acronym));
		return result;
	}
}
