package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.participant.aliquot.business.AliquotService;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;


@RunWith(PowerMockRunner.class)
public class TransportationLotFacadeTest {

	private static final String USER_EMAIL = "otus@otus.com";
	private static final String ID = "123456789";

	@InjectMocks
	private TransportationLotFacade transportationLotFacade;
	@Mock
	private TransportationLotService transportationLotService;
	@Mock
	private TransportationAliquotFiltersDTO transportationAliquotFiltersDTO;
	@Mock
	private TransportationLot transportationLot;
	@Mock
	private AliquotService aliquotService;

	@Test
	public void createMethod_should_invoke_createService() throws ValidationException, DataNotFoundException {
		transportationLotFacade.create(transportationLot, USER_EMAIL);
		verify(transportationLotService, times(1)).create(transportationLot, USER_EMAIL);
	}

	@Test(expected = HttpResponseException.class)
	public void createMethod_should_capture_ValidationException() throws ValidationException, DataNotFoundException {
		ValidationException e = PowerMockito.spy(new ValidationException());
		doNothing().when(e).printStackTrace();
		doThrow(e).when(transportationLotService).create(transportationLot, USER_EMAIL);
		transportationLotFacade.create(transportationLot, USER_EMAIL);
	}

	@Test(expected = HttpResponseException.class)
	public void createMethod_should_capture_DataNotFoundException() throws ValidationException, DataNotFoundException {
		DataNotFoundException e = PowerMockito.spy(new DataNotFoundException());
		doNothing().when(e).printStackTrace();
		doThrow(e).when(transportationLotService).create(transportationLot, USER_EMAIL);
		transportationLotFacade.create(transportationLot, USER_EMAIL);
	}

	@Test
	public void getLotsMethod_should_invoke_listService() {
		transportationLotFacade.getLots();
		verify(transportationLotService, times(1)).list();
	}

	@Test
	public void updateMethod_should_invoke_updateService() throws ValidationException, DataNotFoundException {
		transportationLotFacade.update(transportationLot);
		verify(transportationLotService, times(1)).update(transportationLot);
	}

	@Test(expected = HttpResponseException.class)
	public void updateMethod_should_capture_DataNotFoundException() throws ValidationException, DataNotFoundException {
		DataNotFoundException e = spy(new DataNotFoundException());
		doNothing().when(e).printStackTrace();
		doThrow(e).when(transportationLotService).update(transportationLot);
		transportationLotFacade.update(transportationLot);
	}

	@Test(expected = HttpResponseException.class)
	public void updateMethod_should_capture_ValidationException() throws ValidationException, DataNotFoundException {
		ValidationException e = spy(new ValidationException());
		doNothing().when(e).printStackTrace();
		doThrow(e).when(transportationLotService).update(transportationLot);
		transportationLotFacade.update(transportationLot);
	}

	@Test
	public void deleteMethod_should_invoke_deleteService() throws ValidationException, DataNotFoundException {
		transportationLotFacade.delete(ID);
		verify(transportationLotService, times(1)).delete(ID);
	}

	@Test(expected = HttpResponseException.class)
	public void deleteMethod_should_capture_DataNotFoundException() throws ValidationException, DataNotFoundException {
		DataNotFoundException e = spy(new DataNotFoundException());
		doNothing().when(e).printStackTrace();
		doThrow(e).when(transportationLotService).delete(ID);
		transportationLotFacade.delete(ID);
	}

	@Test
	public void getAliquotsMethod_should_invoke_getAliquotsService() throws ValidationException, DataNotFoundException {
		transportationLotFacade.getAliquots();
		verify(aliquotService, times(1)).getAliquots();
	}

	@Test
	public void getAliquotsByPeriodMethod_should_invoke_getAliquotsByPeriodService() throws DataNotFoundException {
		transportationLotFacade.getAliquotsByPeriod(transportationAliquotFiltersDTO);
		verify(aliquotService, times(1)).getAliquotsByPeriod(transportationAliquotFiltersDTO);
	}

	@Test(expected = HttpResponseException.class)
	public void getAliquotsByPeriodMethod_should_capture_DataNotFoundException() throws  DataNotFoundException {
		DataNotFoundException e = spy(new DataNotFoundException());
		doNothing().when(e).printStackTrace();
		doThrow(e).when(aliquotService).getAliquotsByPeriod(transportationAliquotFiltersDTO);
		transportationLotFacade.getAliquotsByPeriod(transportationAliquotFiltersDTO);
	}

	@Test
	public void getAliquot_should_invoke_getAliquotService() throws DataNotFoundException, ValidationException {
		transportationLotFacade.getAliquot(transportationAliquotFiltersDTO);
		verify(aliquotService, times(1)).getAliquot(transportationAliquotFiltersDTO);
	}


	@Test(expected = HttpResponseException.class)
	public void getAliquotMethod_should_capture_DataNotFoundException() throws DataNotFoundException, ValidationException {
		DataNotFoundException e = spy(new DataNotFoundException());
		doNothing().when(e).printStackTrace();
		doThrow(e).when(aliquotService).getAliquot(transportationAliquotFiltersDTO);
		transportationLotFacade.getAliquot(transportationAliquotFiltersDTO);
	}

	@Test(expected = HttpResponseException.class)
	public void getAliquotMethod_should_capture_ValidationException() throws DataNotFoundException, ValidationException {
		ValidationException e = spy(new ValidationException());
		doNothing().when(e).printStackTrace();
		doThrow(e).when(aliquotService).getAliquot(transportationAliquotFiltersDTO);
		transportationLotFacade.getAliquot(transportationAliquotFiltersDTO);
	}
}