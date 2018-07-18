package org.ccem.otus.validators;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;

public class FieldCenterValidator {
  private static final String ATTRIBUTE_ALREADY_EXISTS_MESSAGE = "FieldCenter Attribute already exists.";
  private static final String acronym = "acronym";
  private static final String code = "code";
  private static final boolean state = false;
  
  @Inject
  private FieldCenterDao fieldCenterDao;
  private FieldCenterValidationResult fieldCenterValidationResult;
  
  public FieldCenterValidator(FieldCenterDao fieldCenterDao) {
    this.fieldCenterDao = fieldCenterDao;
    this.fieldCenterValidationResult = new FieldCenterValidationResult();
  }

  public void validate(FieldCenter fielCenter) throws ValidationException {
    _checkForFieldCenterExistent(fielCenter);
    if (!fieldCenterValidationResult.isValid()) {
      throw new ValidationException(new Throwable(ATTRIBUTE_ALREADY_EXISTS_MESSAGE),
          fieldCenterValidationResult);
    }
  }

  private void _checkForFieldCenterExistent(FieldCenter fielCenter) {
    if (fieldCenterDao.acronymInUse(fielCenter.getAcronym())) {
      fieldCenterValidationResult.pushConflict(acronym);
      fieldCenterValidationResult.setValid(state);
    }
    if (fieldCenterDao.codeInUse(fielCenter.getCode())) {
      fieldCenterValidationResult.pushConflict(code);
      fieldCenterValidationResult.setValid(state);
    }
  }
}
