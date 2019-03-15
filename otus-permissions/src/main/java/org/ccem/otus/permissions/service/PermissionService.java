package org.ccem.otus.permissions.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface PermissionService {

  void getAll(String email) throws DataNotFoundException;

}
