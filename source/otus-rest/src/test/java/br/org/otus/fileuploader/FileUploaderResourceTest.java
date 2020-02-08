package br.org.otus.fileuploader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.ccem.otus.model.FileUploaderPOJO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.fileuploader.api.FileUploaderFacade;
import br.org.otus.rest.Response;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileUploaderResource.class, Response.class, javax.ws.rs.core.Response.class})
public class FileUploaderResourceTest {
  private static final String OID = "592415fb28110d2722b16fe3";
  private static final String UPLOAD_OK = "uploadOK";
  private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoicGFydGljaXBhbnQiLCJpc3MiOiJib2VzZS53b3JrQGdtYWlsLmNvbSJ9.2yWrdhnF9P3sqDlq75Mlwz4mb7UvXRdpNi1IIxPfeQk";
//  private HttpServletRequest request = new HttpServletRequest();
  @InjectMocks
  private FileUploaderResource fileUploaderResource;
  @Mock
  private FileUploaderFacade facade;
  @Mock
  private FileUploaderPOJO form;
  @Mock
  private InputStream stream;
  @Mock
  private ResponseBuilder builder;
  @Mock
  private javax.ws.rs.core.Response responseJx;
  private String responsePostExpected;

  @Test
  public void method_getById_should_return_javaxWsRsCoreResponseInstance() throws Exception {
    mockStatic(javax.ws.rs.core.Response.class);
    when(javax.ws.rs.core.Response.ok(any())).thenReturn(builder);
    when(builder.build()).thenReturn(responseJx);
    assertTrue((fileUploaderResource.getById(OID)) instanceof javax.ws.rs.core.Response);
  }

  @Test
  public void method_delete() {
    assertTrue(fileUploaderResource.delete(OID) instanceof javax.ws.rs.core.Response);
    verify(facade).delete(OID);
  }
}
