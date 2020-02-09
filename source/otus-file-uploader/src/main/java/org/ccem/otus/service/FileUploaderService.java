package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploader;

public interface FileUploaderService {

  String upload(FileUploader file);

  byte[] getById(String oid) throws DataNotFoundException;

  void delete(String oid) throws DataNotFoundException;
}
