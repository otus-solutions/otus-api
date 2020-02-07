package br.org.otus.fileuploader.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FileUploaderPOJO;
import org.ccem.otus.service.download.ZipFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mongodb.client.gridfs.GridFSBucket;

import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.response.exception.HttpResponseException;

@RunWith(MockitoJUnitRunner.class)
public class FileUploaderFacadeTest {
  private static final String OID = "592415fb28110d2722b16fe3";
  private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImJvZXNlLndvcmtAZ21haWwuY29tIn0.mwjgTibwvxjnZyUyWJH5N97tEw9bzLIbVOmfZnF_WWM";

  @InjectMocks
  private FileUploaderFacade fileUploaderFacade;
  @Mock
  private FileStoreBucket fileStoreBucket;
  @Mock
  private FileDownloadService fileDownloadService;
  @Mock
  private GridFSBucket fileStore;
  @Mock
  private InputStream stream;
  @Mock
  private Document firstGetMetadata;
  @Mock
  private FileUploaderPOJO form;
  private ObjectId objectId;

  @Test
  public void method_getById_should_return_stream() throws DataNotFoundException {
    when(fileStoreBucket.download(OID)).thenReturn(stream);
    assertTrue((fileUploaderFacade.getById(OID)) instanceof InputStream);
  }

  @Test(expected = HttpResponseException.class)
  public void method_getById_should_throw_DataNotFoundException() throws Exception {
    when(fileStoreBucket.download(OID)).thenThrow(new DataNotFoundException(new Exception()));
    fileUploaderFacade.getById(OID);
  }

  @Test
  public void method_Delete_should_evocate_fileStoreBuckerDeleteMethod() throws DataNotFoundException {
    when(fileStoreBucket.findMetadata(OID)).thenReturn(firstGetMetadata);
    fileUploaderFacade.delete(OID);
    verify(fileStoreBucket).delete(OID);
  }

  @Test(expected = HttpResponseException.class)
  public void method_delete_should_throw_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException(new Exception())).when(fileStoreBucket).delete(OID);
    fileUploaderFacade.delete(OID);
  }

  @Test
  public void method_Upload_should_return_objectIdString() {
    form = new FileUploaderPOJO();
    objectId = new ObjectId(OID);
    when(fileStoreBucket.store(form)).thenReturn(objectId.toString());
    assertEquals(fileUploaderFacade.upload(form, TOKEN), objectId.toString());
  }

  @Test
  public void method_download_files_should_call_proper_service() throws DataNotFoundException, IOException, ValidationException {
    ArrayList<String> objectIds = new ArrayList<>();
    ZipFactory.Zip zip = ZipFactory.create(Arrays.asList());
    Mockito.when(fileDownloadService.downloadFiles(Mockito.any())).thenReturn(zip);

    fileUploaderFacade.downloadFiles(objectIds);
    Mockito.verify(fileDownloadService).downloadFiles(objectIds);
  }

  @Test(expected = HttpResponseException.class)
  public void method_download_files_should_handle_the_exceptions() throws DataNotFoundException, IOException, ValidationException {
    ArrayList<String> objectIds = new ArrayList<>();

    doThrow(new DataNotFoundException(new Throwable(""), Arrays.asList())).when(fileDownloadService).downloadFiles(Mockito.any());
    fileUploaderFacade.downloadFiles(objectIds);
    Mockito.verify(fileDownloadService).downloadFiles(objectIds);
  }
}
