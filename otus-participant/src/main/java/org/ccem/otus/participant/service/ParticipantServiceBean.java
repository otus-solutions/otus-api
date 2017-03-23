package org.ccem.otus.participant.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

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

	@Override
	public Participant getByRecruitmentNumber(long rn) throws DataNotFoundException {
		return participantDao.findByRecruitmentNumber(rn);
	}

}
