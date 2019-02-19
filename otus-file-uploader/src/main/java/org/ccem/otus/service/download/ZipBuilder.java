package org.ccem.otus.service.download;

import org.apache.commons.io.IOUtils;

import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipBuilder {


  public static Zip create(List<FileDownload> files) throws IOException {

    Zip zip = new Zip();

    ZipOutputStream zipOut = new ZipOutputStream(zip);

    for (FileDownload fileInfo : files) {
      InputStream inputStream = fileInfo.getFileStream();

      byte[] bytes;

      String fileName = fileInfo.getName();
      String fileExtension = fileName.substring(fileName.lastIndexOf("."));

      String outputName = fileInfo.getOid() + fileExtension;

      bytes = IOUtils.toByteArray(inputStream);
      zipOut.putNextEntry(new ZipEntry(outputName));
      zipOut.write(bytes, 0, bytes.length);
      zipOut.closeEntry();



    }

    zipOut.close();
    return zip;

  }


  public static class Zip extends ByteArrayOutputStream {

    Zip() {
    }

    public Response buildResponse() throws IOException {
      Response.ResponseBuilder builder = Response.ok(this.toByteArray());
      builder.header("Content-Disposition", "attachment; filename=" + "file-extraction");
      Response response = builder.build();

      this.close();

      return response;
    }
  }
}
