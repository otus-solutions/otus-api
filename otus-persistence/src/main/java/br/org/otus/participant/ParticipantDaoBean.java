package br.org.otus.participant;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.Participant;
import org.ccem.otus.persistence.ParticipantDao;

import com.google.gson.Gson;
import com.mongodb.Block;

import br.org.mongodb.MongoGenericDao;

public class ParticipantDaoBean extends MongoGenericDao implements ParticipantDao {
	private static final String COLLECTION_NAME = "Participant";

	public ParticipantDaoBean() {
		super(COLLECTION_NAME);
	}

	@Override
	public void persist(Participant participant) {
		super.persist(new Gson().toJson(participant));
	}

	@Override
	public ArrayList<Participant> find() {
		ArrayList<Participant> participants = new ArrayList<>();
		list().forEach((Block<Document>) document -> {
			participants.add(new Gson().fromJson(document.toJson(), Participant.class));
		});

		return participants;
	}
}
