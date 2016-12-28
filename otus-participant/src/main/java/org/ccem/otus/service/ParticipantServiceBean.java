package org.ccem.otus.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.model.Participant;
import org.ccem.otus.persistence.ParticipantDao;

@Stateless
public class ParticipantServiceBean implements ParticipantService {

	@Inject
	private ParticipantDao participantDao;

	@Override
	public void create(Set<Participant> participants) {
		participants.forEach(participant -> create(participant));
	}

	@Override
	public void create(Participant participant) {
		participantDao.persist(participant);
	}

	@Override
	public List<Participant> list() {
		return participantDao.find();
	}

}
