package br.org.otus.fileuploader.api;

import br.org.mongodb.gridfs.FileStoreBucket;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.service.download.FileDownload;
import org.ccem.otus.service.download.ZipFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ZipFactory.class)
public class FileDownloadServiceTest {

  @InjectMocks
  private FileDownloadService service;

  @Mock
  private FileStoreBucket fileStoreBucket;

  private ArrayList<String> objectIdStrings;
  private ArrayList<ObjectId> objectIds;
  private ArrayList<FileDownload> files;

  @Before
  public void setUp() throws Exception {

    objectIdStrings = new ArrayList<>(Arrays.asList(
      "5c6164e66154a8006654368c",
      "5a38082e28f10d10437110a0",
      "5a37fa0028f10d104371104e",
      "593aebd228f10d478d73c389"
    ));

    objectIds = new ArrayList<>(Arrays.asList(
      new ObjectId("5c6164e66154a8006654368c"),
      new ObjectId("5a38082e28f10d10437110a0"),
      new ObjectId("5a37fa0028f10d104371104e"),
      new ObjectId("593aebd228f10d478d73c389")
    ));

    files = new ArrayList<>();
    objectIdStrings.stream().forEach(objectId -> {
      FileDownload fileDownload = new FileDownload(objectId, "name", IOUtils.toInputStream("test"));
      files.add(fileDownload);
    });

    Mockito.when(fileStoreBucket.fetchFiles(objectIds)).thenReturn(files);
  }

  @Test
  public void downloadFiles_should_call_zip_factory_with_files() throws Exception {
    PowerMockito.mockStatic(ZipFactory.class);

    ZipFactory.Zip zip = ZipFactory.create(files);
    ZipFactory.Zip zip1 = service.downloadFiles(objectIdStrings);

    Assert.assertEquals(zip, zip1);
  }

  @Test
  public void downloadFiles_should_call_fileStoreBucket_with_converted_ids() throws DataNotFoundException, IOException, ValidationException {
    service.downloadFiles(objectIdStrings);

    Mockito.verify(fileStoreBucket).fetchFiles(objectIds);
  }

  @Test(expected = ValidationException.class)
  public void downloadFiles_should_handle_malformed_oid() throws DataNotFoundException, IOException, ValidationException {
    objectIdStrings.add("593aebd2c38");

    service.downloadFiles(objectIdStrings);
  }
}