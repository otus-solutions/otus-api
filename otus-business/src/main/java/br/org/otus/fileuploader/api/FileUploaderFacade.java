package br.org.otus.fileuploader.api;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploaderPOJO;

import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

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
    List<ObjectId> objectIds = oids.stream().map(ObjectId::new).collect(Collectors.toList());
    try {

      List<InputStream> files = fileStoreBucket.downloadMultiple(objectIds);
      InputStream inputStream = files.get(0);



//      Enumeration inputStreams = Collections.enumeration(files);
//      SequenceInputStream sis = new SequenceInputStream(inputStreams);


      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ZipOutputStream out = new ZipOutputStream(baos);

      //


      byte[] bytes = IOUtils.toByteArray(inputStream);

      out.putNextEntry(new ZipEntry("download"));
      out.write(bytes, 0, bytes.length);
      out.closeEntry();

      //

      out.close();

      Response.ResponseBuilder builder = Response.ok(baos.toByteArray());
      builder.header("Content-Disposition", "attachment; filename=" + "anything");
      Response response = builder.build();

      baos.close();

      return response;


    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }



}
