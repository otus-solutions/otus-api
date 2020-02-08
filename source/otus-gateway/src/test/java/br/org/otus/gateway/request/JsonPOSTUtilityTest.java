package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonPOSTUtility.class, URL.class, DataOutputStream.class, RequestUtility.class})
public class JsonPOSTUtilityTest {
  private static String BODY = "{\"recruitmentNumber\": \"4107\",\"variables\":[{\"name\": \"tst1\",\"value\": \"Text\",\"sending\": \"1\"},{\"name\": \"tst1\",\"value\": \"Text\",\"sending\": \"2\"}]}";
  private static String CONNECTION_CLOSED = "Stream closed";
  private static final String RESPONSE_RESULT = "success: connection finalized ";

  private JsonPOSTUtility jsonPOSTUtility;
  private URL requestURL;
  @Mock
  private HttpURLConnection httpConn;
  @Mock
  private DataOutputStream request;
  @Mock
  private OutputStream outputStream;

  @Before
  public void setUp() throws Exception {
    requestURL = mock(URL.class);
    when((HttpURLConnection) requestURL.openConnection()).thenReturn(httpConn);
    when(httpConn.getOutputStream()).thenReturn(outputStream);
    whenNew(DataOutputStream.class).withArguments(outputStream).thenReturn(request);
    jsonPOSTUtility = new JsonPOSTUtility(requestURL, BODY);
  }

  @Test
  public void test_should_verify_if_internalConstructorCalls_are_being_invoked() throws IOException {
    verify(httpConn, times(1)).setUseCaches(false);
    verify(httpConn, times(1)).setDoOutput(true);
    verify(httpConn, times(1)).setDoInput(true);
    verify(httpConn, times(1)).setRequestMethod("POST");
    verify(httpConn, times(1)).setRequestProperty("Connection", "Keep-Alive");
    verify(httpConn, times(1)).setRequestProperty("Cache-Control", "no-cache");
    verify(httpConn, times(1)).setRequestProperty("Content-Type", "application/json");
    verify(request, times(1)).write(BODY.getBytes("UTF-8"));
  }

  @Test
  public void finishMethod_should_returns_information_about_properly_closed_connection() throws IOException {
    when(httpConn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
    mockStatic(RequestUtility.class);
    when(RequestUtility.getString(httpConn)).thenReturn(RESPONSE_RESULT);
    assertEquals(jsonPOSTUtility.finish(), RESPONSE_RESULT);
  }

  @Test
  public void test_should_catch_IOException_of_finishMethod() throws IOException {
    when(httpConn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
    try {
      jsonPOSTUtility.finish();
    } catch (IOException e) {
      assertEquals(e.getMessage(), CONNECTION_CLOSED);
    }
  }
}