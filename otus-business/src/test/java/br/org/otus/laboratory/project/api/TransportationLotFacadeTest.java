package br.org.otus.laboratory.project.api;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;
import br.org.otus.response.exception.HttpResponseException;




@RunWith(PowerMockRunner.class)
public class TransportationLotFacadeTest {
	
	@InjectMocks
	private TransportationLotFacade transportationLotFacade;
	@Mock
	private TransportationLotService transportationLotService;
	@Mock
	private WorkAliquotFiltersDTO workAliquotFiltersDTO;
	

	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void method_getAliquotsByPeriod_should_call_service() throws DataNotFoundException {
		transportationLotFacade.getAliquotsByPeriod(workAliquotFiltersDTO);
		verify(transportationLotService, times(1)).getAliquotsByPeriod(workAliquotFiltersDTO);		
	}
	
	@Test(expected = HttpResponseException.class)
	public void method_getAliquotsByPeriod_should_treat_DataNotFoundException() throws DataNotFoundException {
		
		DataNotFoundException e = PowerMockito.spy(new DataNotFoundException());
		Mockito.doNothing().when(e).printStackTrace();
		Mockito.doThrow(e).when(transportationLotService).getAliquotsByPeriod(workAliquotFiltersDTO);
		transportationLotFacade.getAliquotsByPeriod(workAliquotFiltersDTO);				
	}
	
	

	@Test
	public void testGetAliquot() throws ValidationException {
		transportationLotFacade.getAliquot(workAliquotFiltersDTO);
		verify(transportationLotService, times(1)).getAliquot(workAliquotFiltersDTO);		
	}
	
	@Test(expected = HttpResponseException.class)
	public void method_getAliquot_should_treat_DataNotFoundException() throws ValidationException {
		
		ValidationException e = PowerMockito.spy(new ValidationException());
		Mockito.doNothing().when(e).printStackTrace();
		Mockito.doThrow(e).when(transportationLotService).getAliquot(workAliquotFiltersDTO);
		transportationLotFacade.getAliquot(workAliquotFiltersDTO);				
	}

}
