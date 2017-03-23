package org.ccem.otus.participant.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;

import java.util.List;
import java.util.Set;

public interface ParticipantService {

    void create(Set<Participant> participants);

    void create(Participant participant);
    
    List<Participant> list();
    
    Participant getByRecruitmentNumber(long rn) throws DataNotFoundException;
}
