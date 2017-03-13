package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.Participant;

import java.util.ArrayList;

public interface ParticipantDao {

    void persist(Participant participant);
    
    ArrayList<Participant> find();
    
    Participant findByRecruitmentNumber(long rn) throws DataNotFoundException;
}
