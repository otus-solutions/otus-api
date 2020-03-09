package br.org.otus.fieldCenter.api;

import java.util.List;

import javax.inject.Inject;

import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import br.org.otus.laboratory.project.transportation.business.TransportLocationPointService;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.service.FieldCenterService;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class FieldCenterFacade {

  @Inject
  private FieldCenterService fieldCenterService;
  @Inject
  private TransportLocationPointService transportLocationPointService;

  public void create(FieldCenter fieldCenter) {
    try {
      TransportLocationPoint transportLocationPoint = new TransportLocationPoint(fieldCenter.getName());
      fieldCenter.setLocationPoint(transportLocationPoint.get_id());
      fieldCenterService.create(fieldCenter);
      transportLocationPointService.create(transportLocationPoint);
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
