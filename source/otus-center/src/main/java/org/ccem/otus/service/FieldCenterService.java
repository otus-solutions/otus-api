package org.ccem.otus.service;


import java.util.List;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;

public interface FieldCenterService {
  void create(FieldCenter fieldCenterDto) throws ValidationException;

  void update(FieldCenter fieldCenterUpdateDto) throws ValidationException;

  List<FieldCenter> list();

  FieldCenter fetchByAcronym(String acronym);
}
