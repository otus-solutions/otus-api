package org.ccem.otus.persistence;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.Participant;

public interface ParticipantDao {

    void persist(Participant participant);
    
    ArrayList<Participant> find();
    
    Participant findByRecruitmentNumber(long rn) throws DataNotFoundException;
}
