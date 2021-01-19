package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonPUTRequestUtility.class, URL.class, DataOutputStream.class, RequestUtility.class})
public class JsonPUTUtilityTest {

  private static String CONNECTION_CLOSED = "closed";
  private static final String RESPONSE_RESULT = "success";

  private JsonPUTRequestUtility jsonPUTRequestUtility;
  private URL requestURL;
  @Mock
  private HttpURLConnection httpConn;
  @Mock
  private DataOutputStream request;
  @Mock
  private OutputStream outputStream;

  @Before
  public void setUp() throws Exception {
    mockStatic(RequestUtility.class);
    requestURL = mock(URL.class);
    when((HttpURLConnection) requestURL.openConnection()).thenReturn(httpConn);
    when(httpConn.getOutputStream()).thenReturn(outputStream);
    whenNew(DataOutputStream.class).withArguments(outputStream).thenReturn(request);
    jsonPUTRequestUtility = new JsonPUTRequestUtility(requestURL);
  }

  @Test
  public void test_should_verify_if_internalConstructorCalls_are_being_invoked() throws IOException {
    verify(httpConn, times(1)).setUseCaches(false);
    verify(httpConn, times(1)).setDoOutput(true);
    verify(httpConn, times(1)).setDoInput(true);
    verify(httpConn, times(1)).setRequestMethod(RequestTypeOptions.PUT.getName());
    verify(httpConn, times(1)).setRequestProperty("Connection", "Keep-Alive");
    verify(httpConn, times(1)).setRequestProperty("Cache-Control", "no-cache");
    verify(httpConn, times(1)).setRequestProperty("Content-Type", "application/json");
  }

  @Test
  public void finish_method_should_returns_information_about_properly_closed_connection() throws IOException {
    when(httpConn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
    mockStatic(RequestUtility.class);
    when(RequestUtility.getString(httpConn)).thenReturn(RESPONSE_RESULT);
    assertEquals(jsonPUTRequestUtility.finish(), RESPONSE_RESULT);
  }

  @Test
  public void finish_method_should_catch_IOException() throws IOException {
    when(httpConn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
    try {
      jsonPUTRequestUtility.finish();
    } catch (IOException e) {
      assertEquals(e.getMessage(), CONNECTION_CLOSED);
    }
  }

  @Test(expected = RequestException.class)
  public void finish_method_should_throw_RequestException() throws IOException {
    when(httpConn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
    jsonPUTRequestUtility.finish();
  }

  @Test
  public void writeBody_method_should_call_request_write_method() throws IOException {
    String body = mock(String.class);
    Whitebox.setInternalState(jsonPUTRequestUtility, "request", request);
    jsonPUTRequestUtility.writeBody(body);
    verify(request, Mockito.times(1)).write(body.getBytes(StandardCharsets.UTF_8));
  }

}