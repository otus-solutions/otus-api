package org.ccem.otus.service;

import java.util.List;
import java.util.Set;

import org.ccem.otus.model.Participant;

public interface ParticipantService {

    void create(Set<Participant> participants);

    void create(Participant participant);
    
    List<Participant> list();
}
