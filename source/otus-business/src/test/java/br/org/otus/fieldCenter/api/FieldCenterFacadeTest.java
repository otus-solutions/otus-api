package br.org.otus.fieldCenter.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.org.otus.laboratory.project.transportation.business.TransportLocationPointService;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.service.FieldCenterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.response.exception.HttpResponseException;
import org.powermock.api.mockito.PowerMockito;

@RunWith(MockitoJUnitRunner.class)
public class FieldCenterFacadeTest {
  private static final String FIELDCENTER_ACRONYM = "SP";
  private static final String FIELDCENTER_NAME = "Sao Paulo";
  private static final int FIELDCENTER_CODE = 6;
  @InjectMocks
  private FieldCenterFacade fieldCenterFacade;
  @Mock
  private FieldCenterService fieldCenterService;
  @Mock
  private TransportLocationPointService transportLocationPointService;

  private FieldCenter fieldCenter;


  @Before
  public void setUp() {
    fieldCenter = new FieldCenter();
    fieldCenter.setAcronym(FIELDCENTER_ACRONYM);
    fieldCenter.setName(FIELDCENTER_NAME);
    fieldCenter.setCode(FIELDCENTER_CODE);
  }

  @Test
  public void createMethod_should_evocate_create_of_fieldCenterService()
    throws Exception {
    fieldCenterFacade.create(fieldCenter);
    verify(fieldCenterService, times(1)).create(fieldCenter);
    verify(transportLocationPointService, times(1)).create(any());
  }

  @Test(expected = HttpResponseException.class)
  public void createMethod_should_capture_ValidationException() throws ValidationException {
    doThrow(ValidationException.class).when(fieldCenterService).create(fieldCenter);
    fieldCenterFacade.create(fieldCenter);
  }

  @Test
  public void listMethod_should_invoke_list_of_fieldCenterService() {
    fieldCenterFacade.list();
    verify(fieldCenterService, times(1)).list();
  }

  @Test
  public void updateMethod_should_invoke_update_of_fieldCenterService() throws ValidationException {
    fieldCenterFacade.update(fieldCenter);
    verify(fieldCenterService, times(1)).update(fieldCenter);
  }

  @Test(expected = HttpResponseException.class)
  public void updateMethod_should_capture_ValidationException() throws ValidationException {
    doThrow(ValidationException.class).when(fieldCenterService).update(fieldCenter);
    fieldCenterFacade.update(fieldCenter);
  }
}
