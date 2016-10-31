package org.ccem.otus.service;

import org.ccem.otus.model.Participant;

import java.util.Set;

public interface ParticipantService {

    void create(Set<Participant> participants);

    void create(Participant participant);
}
