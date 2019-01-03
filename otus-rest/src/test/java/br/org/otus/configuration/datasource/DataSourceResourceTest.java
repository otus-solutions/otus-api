package br.org.otus.configuration.datasource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.model.DataSource;
import org.ccem.otus.utils.CsvToJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.datasource.api.DataSourceFacade;
import br.org.otus.rest.Response;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DataSourceResource.class)
public class DataSourceResourceTest {
	@Mock
	CsvToJson csvToJson;
	@Mock
	DataSourceFormPOJO form;
	@InjectMocks
	DataSourceResource dataSourceResource;
	@Mock
	DataSourceFacade dataSourceFacade;

	private DataSource dataSource;
	private List<DataSource> dataSourceList;
	private String idValid;

	@Before
	public void setUp() {
		dataSource = DataSource.deserialize(DataSourceFactory.create().toString());
		dataSourceList = new ArrayList<>();
		dataSourceList.add(dataSource);
	}

	@Test
	public void method_post_should_return_reponse_buildSucess() throws Exception {
		whenNew(CsvToJson.class).withAnyArguments().thenReturn(csvToJson);
		whenNew(DataSource.class).withAnyArguments().thenReturn(dataSource);
		assertEquals(dataSourceResource.post(form), new Response().buildSuccess().toJson());
		verify(dataSourceFacade).create(dataSource);
	}

	@Test
	public void method_put_should_return_reponse_buildSucess() throws Exception {
		whenNew(CsvToJson.class).withAnyArguments().thenReturn(csvToJson);
		whenNew(DataSource.class).withAnyArguments().thenReturn(dataSource);
		assertEquals(dataSourceResource.put(form), new Response().buildSuccess().toJson());
		verify(dataSourceFacade).update(dataSource);
	}

	@Test
	public void method_getAll_should_return_dataSourceList() {
		when(dataSourceFacade.getAll()).thenReturn(dataSourceList);
		assertEquals(new Response().buildSuccess(dataSourceFacade.getAll()).toJson(), dataSourceResource.getAll());
		verify(dataSourceFacade, times(2)).getAll();
	}

	@Test
	public void method_getByID_return_Response_fills_if_id_isValid() {
		idValid = "medicamentos_contraceptivos_hormonais";
		when(dataSourceFacade.getByID(idValid)).thenReturn(dataSource);
		assertEquals(new Response().buildSuccess(dataSourceFacade.getByID(idValid)).toJson(),
				dataSourceResource.getByID(idValid));
		verify(dataSourceFacade, times(2)).getByID(idValid);
	}

}
