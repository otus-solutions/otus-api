package org.ccem.otus.service;


import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;

import java.util.List;

public interface FieldCenterService {
    void create(FieldCenter fieldCenterDto) throws AlreadyExistException, ValidationException;

    void update(FieldCenter fieldCenterUpdateDto) throws ValidationException;

    List<FieldCenter> list();
}
