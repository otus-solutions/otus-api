package org.ccem.otus.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;
import org.ccem.otus.validators.FieldCenterValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ FieldCenterServiceBean.class })
public class FieldCenterServiceBeanTest {
  private static final Boolean POSITIVE_ANSWER = true;
  @InjectMocks
  private FieldCenterServiceBean fieldCenterServiceBean = PowerMockito.spy(new FieldCenterServiceBean());
  @Mock
  private FieldCenterDao fieldCenterDao;
  @Mock
  private FieldCenter fieldCenter;
  @Mock
  private FieldCenterValidator fieldCenterValidator;  

  @Test
  public void createMethod_should_invoke_internal_methods() throws Exception {
    whenNew(FieldCenterValidator.class).withArguments(fieldCenterDao).thenReturn(fieldCenterValidator);
    fieldCenterServiceBean.create(fieldCenter);
    verifyPrivate(fieldCenterServiceBean, times(1)).invoke("_validateFieldCenter", fieldCenter);
    verifyPrivate(fieldCenterValidator, times(1)).invoke("validate", fieldCenter);
    verify(fieldCenterDao, times(1)).persist(fieldCenter);
  }

  @Test
  public void updateMethod_should_invoke_update_of_fieldCenterDao() throws ValidationException {
    when(fieldCenter.isValid()).thenReturn(POSITIVE_ANSWER);
    fieldCenterServiceBean.update(fieldCenter);
    verify(fieldCenterDao, times(1)).update(fieldCenter);
  }

  @Test
  public void listMethod_should_invoke_find_of_fieldCenterDao() {
    fieldCenterServiceBean.list();
    verify(fieldCenterDao, times(1)).find();
  }
}
