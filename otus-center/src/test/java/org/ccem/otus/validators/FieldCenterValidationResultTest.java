package org.ccem.otus.validators;

import static org.powermock.api.mockito.PowerMockito.spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ccem.otus.persistence.FieldCenterDao;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class FieldCenterValidationResultTest {
  
  private static final String CONFLICT = "acronym";
  private FieldCenterValidationResult fieldCenterValidationResult;
  private List<String> expectedListConflits;
  private boolean INVALID_ANSWER = false;
  

  @Before
  public void setUp() throws Exception {
    fieldCenterValidationResult = spy(new FieldCenterValidationResult());
    expectedListConflits = Arrays.asList(CONFLICT);
  }

  @Test
  public void method_pushConflict_should_add_in_list() {
    assertTrue(fieldCenterValidationResult.getValue().isEmpty());
    fieldCenterValidationResult.pushConflict(CONFLICT);
    assertEquals(expectedListConflits, fieldCenterValidationResult.getValue());    
  }

  @Test
  public void method_getValue_should_deliver_contentList() {
    fieldCenterValidationResult.pushConflict(CONFLICT);
    assertEquals(expectedListConflits, fieldCenterValidationResult.getValue());    
  }

  @Test
  public void setValid() {
    fieldCenterValidationResult.setValid(INVALID_ANSWER );
    assertFalse(fieldCenterValidationResult.isValid());
    
  }
  @Test
  public void method_isValid_should_delivery_booleanResult() {
    assertTrue(fieldCenterValidationResult.isValid());
    
  }

}
