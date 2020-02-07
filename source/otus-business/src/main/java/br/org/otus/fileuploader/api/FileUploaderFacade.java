package br.org.otus.fileuploader.api;

import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import com.nimbusds.jwt.SignedJWT;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FileUploaderPOJO;
import org.ccem.otus.model.survey.activity.User;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
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

  public String upload(FileUploaderPOJO form, String token) {
    try {
      token = token.substring("Bearer".length()).trim();
      SignedJWT parsed = SignedJWT.parse(token);
      String email = parsed.getJWTClaimsSet().getClaim("iss").toString();
      form.setInterviewer(email);
    } catch ( ParseException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
    return fileStoreBucket.store(form);
  }

  public byte[] downloadFiles(ArrayList<String> oids) {
    try {
      return fileDownloadService.downloadFiles(oids).getByteArray();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
    } catch (IOException e) {
      throw new HttpResponseException(ResponseBuild.Commons.UnexpectedError.build("Error while generating files"));
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
    }
  }


}
