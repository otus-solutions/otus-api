package org.ccem.otus.service;

import org.ccem.otus.model.Participant;
import org.ccem.otus.persistence.ParticipantDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
        // TODO Validar existencia de numero de recrutamento.
        participantDao.persist(participant);
    }
}
