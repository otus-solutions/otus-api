package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonGETUtility.class, RequestUtility.class})
public class JsonGETUtilityTest {

  private static final String RESPONSE_RESULT = "success";

  private JsonGETUtility jsonGETUtility;

  @Mock
  private HttpURLConnection httpConn;

  private URL requestURL;

  @Before
  public void setUp() throws Exception {
    requestURL = mock(URL.class);
    when((HttpURLConnection) requestURL.openConnection()).thenReturn(httpConn);
    jsonGETUtility = new JsonGETUtility(requestURL);
    mockStatic(RequestUtility.class);
  }

  @Test
  public void test_should_verify_if_internalConstructorCalls_are_being_invoked() throws IOException {
    verify(httpConn, times(1)).setRequestMethod(RequestTypeOptions.GET.getName());
    verify(httpConn, times(1)).setRequestProperty("Connection", "Keep-Alive");
    verify(httpConn, times(1)).setRequestProperty("Cache-Control", "no-cache");
  }

  @Test
  public void finish_method_should_return_response() throws IOException {
    when(httpConn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
    when(RequestUtility.getString(httpConn)).thenReturn(RESPONSE_RESULT);
    assertEquals(RESPONSE_RESULT, jsonGETUtility.finish());
  }

  @Test(expected = RequestException.class)
  public void finish_method_should_throw_RequestException() throws IOException {
    when(httpConn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
    jsonGETUtility.finish();
  }
}
