package org.ccem.otus.persistence;

import org.ccem.otus.model.Participant;

public interface ParticipantDao {

    void persist(Participant participant);
}
