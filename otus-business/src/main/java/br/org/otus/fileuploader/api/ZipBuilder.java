package br.org.otus.fileuploader.api;

import br.org.mongodb.gridfs.FileDownload;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.apache.commons.io.IOUtils;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipBuilder {



  public static Zip create(List<FileDownload> files) {

    Zip zip = new Zip();

    ZipOutputStream zipOut = new ZipOutputStream(zip);

    try {
      files.stream().forEach(fileInfo -> {
        InputStream inputStream = fileInfo.getFileStream();

        byte[] bytes;

        String fileName = fileInfo.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        String outputName = fileInfo.getOid() + fileExtension;

        try {
          bytes = IOUtils.toByteArray(inputStream);
          zipOut.putNextEntry(new ZipEntry(outputName));
          zipOut.write(bytes, 0, bytes.length);
          zipOut.closeEntry();
        } catch (IOException e) {
          //file uploader exception
          throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }catch (Exception e) {
          throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
      });


      zipOut.close();

      return zip;

    } catch (IOException e) {
      //file uploader exception
      throw new HttpResponseException(ResponseBuild.Commons.UnexpectedError.build("Error while generating files"));
    }
    
  }


  public static class Zip extends ByteArrayOutputStream{

    Zip() {}

    public Response buildResponse() {
      Response.ResponseBuilder builder = Response.ok(this.toByteArray());
      builder.header("Content-Disposition", "attachment; filename=" + "file-extraction");
      Response response = builder.build();

      try {
        this.close();
      } catch (IOException e) {
        throw new HttpResponseException(ResponseBuild.Commons.UnexpectedError.build("Error while generating files"));
      }

      return response;
    }
  }
}
