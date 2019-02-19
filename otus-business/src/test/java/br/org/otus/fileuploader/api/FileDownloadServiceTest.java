package br.org.otus.fileuploader.api;

import br.org.mongodb.gridfs.FileStoreBucket;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.service.download.FileDownload;
import org.ccem.otus.service.download.ZipBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class FileDownloadServiceTest {

  @InjectMocks
  private FileDownloadService service;

  @Mock
  private FileStoreBucket fileStoreBucket;

  @Mock
  private ZipBuilder zipBuilder;

  @Mock
  private ZipBuilder.Zip zip;

  private ArrayList<String> objectIdStrings;
  private ArrayList<ObjectId> objectIds;
  private ArrayList<FileDownload> files;

  @Before
  public void setUp() throws Exception {

    objectIdStrings = new ArrayList<>();

    objectIdStrings.add("5c6164e66154a8006654368c");
    objectIdStrings.add("5a38082e28f10d10437110a0");
    objectIdStrings.add("5a37fa0028f10d104371104e");
    objectIdStrings.add("593aebd228f10d478d73c389");

    objectIds = new ArrayList<>();

    objectIds.add(new ObjectId("5c6164e66154a8006654368c"));
    objectIds.add(new ObjectId("5a38082e28f10d10437110a0"));
    objectIds.add(new ObjectId("5a37fa0028f10d104371104e"));
    objectIds.add(new ObjectId("593aebd228f10d478d73c389"));

    zip = ZipBuilder.create(Arrays.asList());

    files = new ArrayList<>();

    objectIdStrings.stream().forEach(objectId -> {
      FileDownload fileDownload = new FileDownload(objectId, "name", new InputStream() {
        @Override
        public int read() throws IOException {
          return 0;
        }
      });

      files.add(fileDownload);
    });


    Mockito.when(fileStoreBucket.fetchFiles(objectIds)).thenReturn(files);
  }

  @Test
  public void downloadFiles() throws DataNotFoundException, IOException, ValidationException {
    service.downloadFiles(objectIdStrings);
    Mockito.verify(ZipBuilder.create(files));
  }

  @Test
  public void downloadFiles_should_call_fileStoreBucket_with_converted_ids() throws DataNotFoundException, IOException, ValidationException {
    service.downloadFiles(objectIdStrings);

    Mockito.verify(fileStoreBucket).fetchFiles(objectIds);
  }

  @Test (expected = ValidationException.class)
  public void downloadFiles_should_handle_malformed_oid() throws DataNotFoundException, IOException, ValidationException {
    objectIdStrings.add("593aebd228f10d478d73c38");

    service.downloadFiles(objectIdStrings);
  }
}