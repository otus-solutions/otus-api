package org.ccem.otus.participant.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;

import java.util.ArrayList;

public interface ParticipantDao {

    void persist(Participant participant);
    
    ArrayList<Participant> find();
    
    Participant findByRecruitmentNumber(Long rn) throws DataNotFoundException;
}
