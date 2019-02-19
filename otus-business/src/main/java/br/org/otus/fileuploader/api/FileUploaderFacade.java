package br.org.otus.fileuploader.api;

import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploaderPOJO;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;

public class FileUploaderFacade {

  @Inject
  private FileStoreBucket fileStoreBucket;

  @Inject
  private FileDownloadService fileDownloadService;

  public InputStream getById(String oid) {
    try {
      return fileStoreBucket.download(oid);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void delete(String oid) {
    try {
      fileStoreBucket.delete(oid);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public String upload(FileUploaderPOJO form) {
    return fileStoreBucket.store(form);
  }

  public Response downloadFiles(ArrayList<String> oids) {
    return fileDownloadService.downloadFiles(oids);
  }


}
