package br.org.otus.gateway.request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.DataOutputStream;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MultipartPOSTUtility.class, URL.class, RequestUtility.class})
public class MultipartPOSTUtilityTest {
    private static String FIELD_NAME = "Text";
    private static String NAME = "Peso";
    private static String VALUE = "60Kg";
    private static final String RESPONSE_RESULT = "success: connection finalized ";
    private static byte[] bytes;

    private MultipartPOSTUtility multipartPOSTUtility;

    private URL requestURL;

    @Mock
    private HttpURLConnection httpConn;

    @Mock
    private DataOutputStream request;

    @Mock
    private OutputStream outputStream;

    private File file;

    @Before
    public void setUp() throws Exception {
        requestURL = mock(URL.class);
        when((HttpURLConnection)requestURL.openConnection()).thenReturn(httpConn);
        when(httpConn.getOutputStream()).thenReturn(outputStream);
        whenNew(DataOutputStream.class).withArguments(outputStream).thenReturn(request);
        multipartPOSTUtility = new MultipartPOSTUtility(requestURL);

        file = File.createTempFile("tmp", null);
        bytes = Files.readAllBytes(file.toPath());
    }

    @Test
    public void addFormFieldMethod_should_add_colection_variable_type_correlation() throws Exception {
        multipartPOSTUtility.addFormField(NAME,VALUE);
        verify(request,times(1)).flush();
    }

    @Test(expected = IOException.class)
    public void addFormFieldMethod_should_throw_exception_for_IOException() throws Exception {
        doThrow(new IOException(new Exception())).when(request).flush();
        multipartPOSTUtility.addFormField(NAME,VALUE);
    }


    @Test
    public void addFilePartMethod_should_add_file_and_fieldName() throws Exception {
        multipartPOSTUtility.addFilePart(FIELD_NAME,file);
        verify(request,times(1)).write(bytes);
    }

    @Test(expected = IOException.class)
    public void addFilePartMethod_should_throw_exception_for_IOException() throws Exception {
        doThrow(new IOException(new Exception())).when(request).write(bytes);
        multipartPOSTUtility.addFilePart(FIELD_NAME,file);
    }

    @Test
    public void finishMethod_should_return_variable_type_correlation() throws Exception {
        when(httpConn.getResponseCode()).thenReturn(200);
        mockStatic(RequestUtility.class);
        when(RequestUtility.getString(httpConn)).thenReturn(RESPONSE_RESULT);
        assertEquals(RESPONSE_RESULT,multipartPOSTUtility.finish());
    }

    @Test(expected = IOException.class)
    public void finishMethod_should_throw_exception_for_IOException() throws Exception {
        when(httpConn.getResponseCode()).thenReturn(400);
        multipartPOSTUtility.finish();
    }
}