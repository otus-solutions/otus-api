package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class FieldCenterServiceBean implements FieldCenterService {
    @Inject
    private FieldCenterDao fieldCenterDao;

    @Override
    public void create(FieldCenter fieldCenter) throws AlreadyExistException, ValidationException {
        if (fieldCenter.isValid()) {

            if (!fieldCenterDao.acronymInUse(fieldCenter.getAcronym())) {
                fieldCenterDao.persist(fieldCenter);

            } else {
                throw new AlreadyExistException();
            }
        } else {
            throw new ValidationException();
        }
    }

    @Override
    public void update(FieldCenter fieldCenter) throws ValidationException {
        if (fieldCenter.isValid()) {
            fieldCenterDao.update(fieldCenter);

        } else {
            throw new ValidationException();
        }
    }

    @Override
    public List<FieldCenter> list() {
        return fieldCenterDao.find();
    }
}
