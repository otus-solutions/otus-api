package org.ccem.otus.persistence;


import org.ccem.otus.model.FieldCenter;

import java.util.ArrayList;

public interface FieldCenterDao {

    Boolean acronymInUse(String acronym);

    FieldCenter fetchByAcronym(String acronym);

    void update(FieldCenter fieldCenter);

    void persist(FieldCenter fieldCenter);

    ArrayList<FieldCenter> find();
}
