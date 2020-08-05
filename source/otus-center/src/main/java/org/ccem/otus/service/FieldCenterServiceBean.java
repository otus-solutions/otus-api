package org.ccem.otus.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;
import org.ccem.otus.validators.FieldCenterValidator;

@Stateless
public class FieldCenterServiceBean implements FieldCenterService {
  @Inject
  private FieldCenterDao fieldCenterDao;

  @Override
  public void create(FieldCenter fieldCenter) throws ValidationException {
    _validateFieldCenter(fieldCenter);
    fieldCenterDao.persist(fieldCenter);
  }

  private void _validateFieldCenter(FieldCenter fieldCenter) throws ValidationException {
    FieldCenterValidator fieldCenterValidator = new FieldCenterValidator(fieldCenterDao);
    fieldCenterValidator.validate(fieldCenter);
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

  @Override
  public FieldCenter fetchByAcronym(String acronym) {
    return fieldCenterDao.fetchByAcronym(acronym);
  }
}
