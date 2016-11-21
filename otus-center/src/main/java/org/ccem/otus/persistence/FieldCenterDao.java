package org.ccem.otus.persistence;


import java.util.ArrayList;

import org.ccem.otus.model.FieldCenter;

public interface FieldCenterDao {

    Boolean acronymInUse(String acronym);

    FieldCenter fetchByAcronym(String acronym);

    void update(FieldCenter fieldCenter);

    void persist(FieldCenter fieldCenter);

    ArrayList<FieldCenter> find();
}
