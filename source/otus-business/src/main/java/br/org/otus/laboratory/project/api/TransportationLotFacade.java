package br.org.otus.laboratory.project.api;

import java.util.List;

import javax.inject.Inject;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.business.AliquotService;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class TransportationLotFacade {

  @Inject
  private TransportationLotService transportationLotService;
  @Inject
  private AliquotService aliquotService;

  public TransportationLot create(TransportationLot transportationLot, String email) {
    try {
      return transportationLotService.create(transportationLot, email);
    } catch (ValidationException e) {
      e.printStackTrace();
      throw new HttpResponseException(
        ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
    } catch (DataNotFoundException e) {
      e.printStackTrace();
      throw new HttpResponseException(
        ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
    }
  }

  public List<TransportationLot> getLots() {
    return transportationLotService.list();
  }

  public TransportationLot update(TransportationLot transportationLot) {
    try {
      return transportationLotService.update(transportationLot);
    } catch (DataNotFoundException e) {
      e.printStackTrace();
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    } catch (ValidationException e) {
      e.printStackTrace();
      throw new HttpResponseException(
        ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
    }
  }

  public void delete(String id) {
    try {
      transportationLotService.delete(id);
    } catch (DataNotFoundException e) {
      e.printStackTrace();
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public List<Aliquot> getAliquots() {
    return aliquotService.getAliquots();
  }

  public List<Aliquot> getAliquotsByPeriod(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO) {
    try {
      return aliquotService.getAliquotsByPeriod(transportationAliquotFiltersDTO);
    } catch (DataNotFoundException e) {
      e.printStackTrace();
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public Aliquot getAliquot(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO) {
    try {
      return aliquotService.getAliquot(transportationAliquotFiltersDTO);
    } catch (DataNotFoundException | ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }
}

