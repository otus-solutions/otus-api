package br.org.otus.fieldCenter.api;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.service.FieldCenterService;
import org.ccem.otus.validators.FieldCenterValidationResult;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class FieldCenterFacade {
  @Inject
  private FieldCenterService fieldCenterService;

  public void create(FieldCenter fieldCenter) {
    try {
      fieldCenterService.create(fieldCenter);

    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage(), e.getData()));
    }
  }

  public List<FieldCenter> list() {
    return fieldCenterService.list();
  }

  public void update(FieldCenter fieldCenterUpdateDto) {
    try {
      fieldCenterService.update(fieldCenterUpdateDto);

    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());

    }
  }
}
