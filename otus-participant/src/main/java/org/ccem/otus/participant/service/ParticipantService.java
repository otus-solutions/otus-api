package org.ccem.otus.participant.service;

import java.util.List;
import java.util.Set;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;

public interface ParticipantService {

    void create(Set<Participant> participants);

    void create(Participant participant);
    
    Participant getByRecruitmentNumber(long rn) throws DataNotFoundException;

	List<Participant> list(FieldCenter fieldCenter);
}
