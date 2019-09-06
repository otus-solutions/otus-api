package br.org.otus.gateway.gates;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.request.MultipartPOSTUtility;
import br.org.otus.gateway.resource.DBDistributionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DBDistributionGateway.class })
public class DBDistributionGatewayTest {
  private static String INFO_VARIABLE_PARAMS = "{\"recruitmentNumber\": \"4107\",\"variables\":[{\"name\": \"tst1\",\"value\": \"Text\",\"sending\": \"1\"},{\"name\": \"tst1\",\"value\": \"Text\",\"sending\": \"2\"}]}";
  private static String CURRENT_VARIABLES_BY_MICROSERVICE = "{\"variables\":[{\"name\": \"var2\",\"sending\": \"1\"}]}";
  private static String HOST = "http://localhost:";
  private static String PORT = "8081";

  @InjectMocks
  private DBDistributionGateway dbDistributionGateway;

  @Mock
  private JsonPOSTUtility jsonPOSTUtility;

  @Mock
  MultipartPOSTUtility multipartPOST;

  @Mock
  private URL requestURL;

  @Mock
  private File file;

  private DBDistributionMicroServiceResources dbDistributionMicroServiceResources = PowerMockito.spy(new DBDistributionMicroServiceResources());

  @Test
  public void findVariablesMethod_should_bring_currentVariableListJson() throws Exception {
    PowerMockito.whenNew(DBDistributionMicroServiceResources.class).withNoArguments().thenReturn(dbDistributionMicroServiceResources);
    Whitebox.setInternalState(dbDistributionMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(dbDistributionMicroServiceResources, "PORT", PORT);

    PowerMockito.when(dbDistributionMicroServiceResources.getFindVariableAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(CURRENT_VARIABLES_BY_MICROSERVICE);
    URL url = new DBDistributionMicroServiceResources().getFindVariableAddress();
    final GatewayResponse response = dbDistributionGateway.find(INFO_VARIABLE_PARAMS, url);
    assertEquals(CURRENT_VARIABLES_BY_MICROSERVICE, response.getData());

    verify(jsonPOSTUtility, times(1)).finish();

  }

  @Test(expected = MalformedURLException.class)
  public void findVariablesMethod_should_throw_exception_for_IOException() throws Exception {
    PowerMockito.when(jsonPOSTUtility.finish()).thenThrow(new IOException(new Throwable("Message")));
    URL url = new DBDistributionMicroServiceResources().getFindVariableAddress();
    dbDistributionGateway.find(INFO_VARIABLE_PARAMS, url);
  }

  @Test
  public void uploadDatabaseMethod_should_add_database_file() throws Exception {
    PowerMockito.whenNew(DBDistributionMicroServiceResources.class).withNoArguments().thenReturn(dbDistributionMicroServiceResources);
    Whitebox.setInternalState(dbDistributionMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(dbDistributionMicroServiceResources, "PORT", PORT);

    PowerMockito.when(dbDistributionMicroServiceResources.getDatabaseUploadAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(MultipartPOSTUtility.class).withAnyArguments().thenReturn(multipartPOST);
    PowerMockito.when(multipartPOST.finish()).thenReturn(CURRENT_VARIABLES_BY_MICROSERVICE);

    dbDistributionGateway.uploadDatabase(file);

    verify(multipartPOST, times(1)).finish();
  }

  @Test(expected = MalformedURLException.class)
  public void uploadDatabaseMethod_should_throw_exception_for_IOException() throws Exception {
    PowerMockito.when(multipartPOST.finish()).thenThrow(new IOException(new Throwable("Message")));
    dbDistributionGateway.uploadDatabase(file);
  }

  @Test
  public void uploadVariableTypeCorrelationMethod_should_add_type_variable_correlation() throws Exception {
    PowerMockito.whenNew(DBDistributionMicroServiceResources.class).withNoArguments().thenReturn(dbDistributionMicroServiceResources);
    Whitebox.setInternalState(dbDistributionMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(dbDistributionMicroServiceResources, "PORT", PORT);

    PowerMockito.when(dbDistributionMicroServiceResources.getVariableTypeCorrelationUploadAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(MultipartPOSTUtility.class).withAnyArguments().thenReturn(multipartPOST);
    PowerMockito.when(multipartPOST.finish()).thenReturn(CURRENT_VARIABLES_BY_MICROSERVICE);

    dbDistributionGateway.uploadVariableTypeCorrelation(file);

    verify(multipartPOST, times(1)).finish();
  }

  @Test(expected = MalformedURLException.class)
  public void uploadVariableTypeCorrelationMethod_should_throw_exception_for_IOException() throws Exception {
    PowerMockito.when(multipartPOST.finish()).thenThrow(new IOException(new Throwable("Message")));
    dbDistributionGateway.uploadVariableTypeCorrelation(file);
  }
}