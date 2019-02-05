package br.org.otus.datasource.api;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.service.DataSourceServiceBean;
import org.ccem.otus.utils.CsvToJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.HttpResponseException;

import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class DataSourceFacadeTest {
	private final byte GET_FILE = (byte) Byte.valueOf(String.valueOf(1010101),2);
	private final String DELIMIT = ";";
	@InjectMocks
	private DataSourceFacade facade;

	@Mock
	private DataSourceServiceBean service;
	@Mock
	private DataSource dataSource;
	@Mock
	private CsvToJson csvToJson;

	@Test(expected = HttpResponseException.class)
	public void test_exception_create() throws AlreadyExistException, ValidationException {
		Exception alreadyExistsException = new AlreadyExistException(new Throwable(""));
		csvToJson = new CsvToJson(DELIMIT, new byte[]{GET_FILE});
		Mockito.doThrow(alreadyExistsException).when(service).create(dataSource, csvToJson.getDuplicatedElements());

		facade.create(dataSource, csvToJson.getDuplicatedElements());
	}

	@Test(expected = HttpResponseException.class)
	public void test_exception_update() throws ValidationException, DataNotFoundException {
		Exception validation = new ValidationException(new Throwable(""));
		csvToJson = new CsvToJson(DELIMIT, new byte[]{GET_FILE});
		Mockito.doThrow(validation).when(service).update(dataSource, csvToJson.getDuplicatedElements());

		facade.update(dataSource, csvToJson.getDuplicatedElements());
	}

	@Test
	public void method_create_should_call_DataSourceServiceBean_create_with_dataSource_argument() throws AlreadyExistException, ValidationException{
		facade.create(dataSource, csvToJson.getDuplicatedElements());
		verify(service).create(dataSource, csvToJson.getDuplicatedElements());
	}

	@Test
	public void method_update_should_call_DataSourceServiceBean_create_with_dataSource_argument() throws ValidationException, DataNotFoundException {
		facade.update(dataSource, csvToJson.getDuplicatedElements());
		verify(service).update(dataSource, csvToJson.getDuplicatedElements());
	}


}
