package br.org.otus.gateway.request;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestUtility.class})
public class RequestUtilityTest {

  private static final String RESPONSE = "{data: \"OK\"}";
  private static final String DATA_VALUE = "OK";
  @Mock
  private HttpURLConnection httpConn;
  @Mock
  private BufferedReader responseStreamReader;
  @Mock
  private StringBuilder stringBuilder;

  @Test
  public void getStringMethod_should_return_value_of_atribute_data_by_response() throws Exception {
    PowerMockito.whenNew(BufferedReader.class).withAnyArguments().thenReturn(responseStreamReader);
    PowerMockito.whenNew(StringBuilder.class).withNoArguments().thenReturn(stringBuilder);
    PowerMockito.when(stringBuilder.toString()).thenReturn(RESPONSE);
    assertTrue(RequestUtility.getString(httpConn).contains(DATA_VALUE));
  }
}