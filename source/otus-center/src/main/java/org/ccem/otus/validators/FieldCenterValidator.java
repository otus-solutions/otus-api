package org.ccem.otus.validators;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;

public class FieldCenterValidator {
  private static final String ATTRIBUTE_ALREADY_EXISTS_MESSAGE = "FieldCenter attributes already exists.";
  private static final String ACRONYM_VALUE = "acronym";
  private static final String CODE_VALUE = "code";
  private static final boolean STATE = false;

  @Inject
  private FieldCenterDao fieldCenterDao;
  private FieldCenterValidationResult fieldCenterValidationResult;

  public FieldCenterValidator(FieldCenterDao fieldCenterDao) {
    this.fieldCenterDao = fieldCenterDao;
    this.fieldCenterValidationResult = new FieldCenterValidationResult();
  }

  public void validate(FieldCenter fieldCenter) throws ValidationException {
    checkForFieldCenterExistent(fieldCenter);
    if (!fieldCenterValidationResult.isValid()) {
      throw new ValidationException(new Throwable(ATTRIBUTE_ALREADY_EXISTS_MESSAGE),
        fieldCenterValidationResult);
    }
  }

  private void checkForFieldCenterExistent(FieldCenter fieldCenter) {
    if (fieldCenterDao.acronymInUse(fieldCenter.getAcronym())) {
      fieldCenterValidationResult.pushConflict(ACRONYM_VALUE);
      fieldCenterValidationResult.setValid(STATE);
    }
    if (fieldCenterDao.codeInUse(fieldCenter.getCode())) {
      fieldCenterValidationResult.pushConflict(CODE_VALUE);
      fieldCenterValidationResult.setValid(STATE);
    }
  }
}
