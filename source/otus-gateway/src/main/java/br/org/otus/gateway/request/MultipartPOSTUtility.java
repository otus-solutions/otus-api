package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class MultipartPOSTUtility extends JsonWritingRequestUtility {

  private static final String BOUNDARY = "*****";
  private static final String CRLF = "\r\n";
  private static final String TWO_HYPHENS = "--";

  public MultipartPOSTUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.POST, requestURL, "multipart/form-data;boundary=" + BOUNDARY);
  }

  public void addFormField(String name, String value) throws IOException {
    request.writeBytes(TWO_HYPHENS + BOUNDARY + CRLF);
    request.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"" + CRLF);
    request.writeBytes("Content-Type: text/plain; charset=UTF-8" + CRLF);
    request.writeBytes(CRLF);
    request.writeBytes(value + CRLF);
    request.flush();
  }

  public void addFilePart(String fieldName, File uploadFile)
    throws IOException {
    String fileName = uploadFile.getName();
    request.writeBytes(TWO_HYPHENS + BOUNDARY + CRLF);
    request.writeBytes("Content-Disposition: form-data; name=\"" +
      fieldName + "\";filename=\"" +
      fileName + "\"" + CRLF);
    request.writeBytes(CRLF);

    byte[] bytes = Files.readAllBytes(uploadFile.toPath());
    request.write(bytes);
  }

  @Override
  public String finish() throws IOException {
    request.writeBytes(CRLF);
    request.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + CRLF);
    try {
      return super.finish();
    }
    catch (RequestException e){
      throw new IOException("Server returned non-OK status: " + e.getErrorCode());
    }
  }
}