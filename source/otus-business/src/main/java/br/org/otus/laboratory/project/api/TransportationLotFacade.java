package br.org.otus.laboratory.project.api;

import java.util.List;

import javax.inject.Inject;

import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.business.AliquotService;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import br.org.otus.model.User;
import br.org.otus.persistence.UserDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class TransportationLotFacade {

  @Inject
  private ParticipantLaboratoryService participantLaboratoryService;
  @Inject
  private TransportationLotService transportationLotService;
  @Inject
  private AliquotService aliquotService;
  @Inject
  private UserDao userDao;

  public TransportationLot create(TransportationLot transportationLot, String email) {
    try {
      User user = userDao.fetchByEmail(email);
      return transportationLotService.create(transportationLot, user.getEmail(), user.get_id());
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

  public List<TransportationLot> getLots(String locationPointId) {
    return transportationLotService.list(locationPointId);
  }

  public TransportationLot getLotByCode(String code) throws DataNotFoundException {
    return transportationLotService.getByCode(code);
  }

  public TransportationLot update(TransportationLot transportationLot, String userEmail) {
    try {
      User user = userDao.fetchByEmail(userEmail);
      return transportationLotService.update(transportationLot,user.get_id());
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

  public List<Aliquot> getAliquotsByPeriod(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO, String locationPointId) {
    try {
      return aliquotService.getAliquotsByPeriod(transportationAliquotFiltersDTO, locationPointId);
    } catch (DataNotFoundException e) {
      e.printStackTrace();
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public Aliquot getAliquot(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO, String locationPointId) {
    try {
      return aliquotService.getAliquot(transportationAliquotFiltersDTO, locationPointId);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
    }
  }

  public Tube getTube(String locationPointId, String tubeCode) {
    try {
      return participantLaboratoryService.getTube(locationPointId, tubeCode);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(),e.getData()));
    }
  }
}

