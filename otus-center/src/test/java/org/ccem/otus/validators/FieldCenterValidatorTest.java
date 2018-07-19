package org.ccem.otus.validators;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class FieldCenterValidatorTest {

  private static final String ACRONYM = "MA";
  private static final Integer CODE = 9;
  private static final Boolean POSITIVE_ANSWER = true;
  private static final Boolean NEGATIVE_ANSWER = false;
  private static final String ACRONYM_VALUE = "acronym";
  private static final String CODE_VALUE = "code";
  private static final boolean STATE = false;
  @Mock
  private FieldCenterDao fieldCenterDao;
  @InjectMocks
  private FieldCenterValidator fieldCenterValidator = spy(new FieldCenterValidator(fieldCenterDao));
  private FieldCenterValidationResult fieldCenterValidationResult = spy(new FieldCenterValidationResult());
  @Mock
  private FieldCenter fieldCenter;

  @Before
  public void setUp() throws Exception {
    when(fieldCenter.getAcronym()).thenReturn(ACRONYM);
    when(fieldCenter.getCode()).thenReturn(CODE);
  }

  @Test
  public void unitTest_should_verify_internal_calls_of_method_validate_() throws Exception {
    when(fieldCenterDao.acronymInUse(ACRONYM)).thenReturn(POSITIVE_ANSWER);
    when(fieldCenterDao.codeInUse(CODE)).thenReturn(POSITIVE_ANSWER);

    doNothing().when(fieldCenterValidationResult, "setValid", STATE);

    fieldCenterValidator.validate(fieldCenter);

    verifyPrivate(fieldCenterDao, times(1)).invoke("acronymInUse", ACRONYM);
    verifyPrivate(fieldCenterValidationResult, times(1)).invoke("pushConflict", ACRONYM_VALUE);
    verifyPrivate(fieldCenterDao, times(1)).invoke("codeInUse", CODE);
    verifyPrivate(fieldCenterValidationResult, times(1)).invoke("pushConflict", CODE_VALUE);
    verifyPrivate(fieldCenterValidationResult, times(2)).invoke("setValid", STATE);
  }

  @Test(expected = ValidationException.class)
  public void method_validate() throws Exception {
    when(fieldCenterValidationResult.isValid()).thenReturn(NEGATIVE_ANSWER);
    fieldCenterValidator.validate(fieldCenter);
  }
}
