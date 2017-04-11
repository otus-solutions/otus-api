package br.org.otus.datasource.api;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.service.DataSourceServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.HttpResponseException;

@RunWith(PowerMockRunner.class)
public class DataSourceFacadeTest {
	
	@InjectMocks
	private DataSourceFacade facade;
	
	@Mock
	private DataSourceServiceBean service;
	@Mock
	private DataSource dataSource;
	
	@Test(expected = HttpResponseException.class)
	public void test_exception() throws AlreadyExistException {
		Exception alreadyExistsException = new AlreadyExistException(new Throwable(""));
		Mockito.doThrow(alreadyExistsException).when(service).create(dataSource);
		
		facade.create(dataSource);
	}
	
	@Test
	public void method_create_should_call_DataSourceServiceBean_create_with_dataSource_argument() throws AlreadyExistException {
		facade.create(dataSource);
		Mockito.verify(service).create(dataSource);
	}

}
