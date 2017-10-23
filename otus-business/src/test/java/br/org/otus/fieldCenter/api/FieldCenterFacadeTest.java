package br.org.otus.fieldCenter.api;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
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

@RunWith(MockitoJUnitRunner.class)
public class FieldCenterFacadeTest {
	private static final String FIELDCENTER_ACRONYM = "SP";
	private static final String FIELDCENTER_NAME = "Sao Paulo";
	private static final int FIELDCENTER_CODE = 6;
	@InjectMocks
	private FieldCenterFacade fieldCenterFacade;
	@Mock
	private FieldCenterService fieldCenterService;
	private FieldCenter fieldCenter;

	@Before
	public void setUp() {
		fieldCenter = new FieldCenter();
		fieldCenter.setAcronym(FIELDCENTER_ACRONYM);
		fieldCenter.setName(FIELDCENTER_NAME);
		fieldCenter.setCode(FIELDCENTER_CODE);
	}

	@Test
	public void method_create_should_evocate_fieldCenterServiceCreateMethod()
			throws AlreadyExistException, ValidationException {
		fieldCenterFacade.create(fieldCenter);
		verify(fieldCenterService).create(fieldCenter);
	}

	@Test(expected = HttpResponseException.class)
	public void method_create_should_capture_AlreadyExistException() throws AlreadyExistException, ValidationException {
		doThrow(AlreadyExistException.class).when(fieldCenterService).create(fieldCenter);
		fieldCenterFacade.create(fieldCenter);
	}

	@Test(expected = HttpResponseException.class)
	public void method_create_should_capture_ValidationException() throws AlreadyExistException, ValidationException {
		doThrow(ValidationException.class).when(fieldCenterService).create(fieldCenter);
		fieldCenterFacade.create(fieldCenter);
	}

	@Test
	public void method_list_should_evocate_fieldCenterServiceListMethod() {
		fieldCenterFacade.list();
		verify(fieldCenterService).list();
	}

	@Test
	public void testUpdate() throws ValidationException {
		fieldCenterFacade.update(fieldCenter);
		verify(fieldCenterService).update(fieldCenter);
	}

	@Test(expected = HttpResponseException.class)
	public void method_update_should_capture_ValidationException() throws ValidationException {
		doThrow(ValidationException.class).when(fieldCenterService).update(fieldCenter);
		fieldCenterFacade.update(fieldCenter);
	}
}
