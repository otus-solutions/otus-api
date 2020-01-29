package br.org.otus.fileuploader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.InputStream;

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
  public void method_post_should_return_ResponseJson() throws IOException {
    responsePostExpected = new br.org.otus.rest.Response().buildSuccess(UPLOAD_OK).toJson();
    when(facade.upload(form)).thenReturn(UPLOAD_OK);
    assertEquals(responsePostExpected, fileUploaderResource.post(form));
  }

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
