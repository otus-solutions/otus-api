package br.org.otus.fileuploader.api;

import br.org.mongodb.gridfs.FileDownload;
import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.exception.ResponseInfo;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploaderPOJO;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUploaderFacade {

  @Inject
  private FileStoreBucket fileStoreBucket;

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

  public Response list(ArrayList<String> oids) {

    List<ObjectId> objectIds = oids.stream().map(s -> {
      try {
        return new ObjectId(s);
      } catch (IllegalArgumentException e) {
        throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage() + " - " + s));
      }
    }).collect(Collectors.toList());

    try {

      List<FileDownload> files = fileStoreBucket.downloadMultiple(objectIds);


      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);


      files.stream().forEach(fileInfo -> {
        InputStream inputStream = fileInfo.getFileStream();

        byte[] bytes;

        try {
          bytes = IOUtils.toByteArray(inputStream);
          zipOut.putNextEntry(new ZipEntry(fileInfo.getName()));
          zipOut.write(bytes, 0, bytes.length);
          zipOut.closeEntry();
        } catch (IOException e) {
          //file uploader exception
          throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }


      });


      zipOut.close();

      Response.ResponseBuilder builder = Response.ok(byteArrayOutputStream.toByteArray());
      builder.header("Content-Disposition", "attachment; filename=" + "anything");
      Response response = builder.build();

      byteArrayOutputStream.close();

      return response;


    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    } catch (IOException e) {
      //file uploader exception
      throw new HttpResponseException(ResponseBuild.Commons.RuntimeError.build("Error while generating files"));
    }
  }


}
