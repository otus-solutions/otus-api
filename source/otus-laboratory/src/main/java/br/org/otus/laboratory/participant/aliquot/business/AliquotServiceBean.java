package br.org.otus.laboratory.participant.aliquot.business;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.transportation.MaterialTrail;
import br.org.otus.laboratory.project.transportation.persistence.MaterialTrackingDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AliquotServiceBean implements AliquotService {
  private static final String ALIQUOT_NOT_IN_LOCATION = "Aliquot is not in transportation lot origin location point";

  @Inject
  private AliquotDao aliquotDao;

  @Inject
  private MaterialTrackingDao materialTrackingDao;

  @Override
  public List<Aliquot> getAliquots() {
    return aliquotDao.getAliquots();
  }

  @Override
  public List<Aliquot> getAliquots(Long rn) {
    return aliquotDao.list(rn);
  }

  @Override
  public Aliquot getAliquot(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO, String locationPointId) throws DataNotFoundException, ValidationException {
    Aliquot aliquot = aliquotDao.getAliquot(transportationAliquotFiltersDTO);
    MaterialTrail materialTrail = materialTrackingDao.getCurrent(transportationAliquotFiltersDTO.getCode());
    if (materialTrail != null && !materialTrail.getLocationPoint().equals(new ObjectId(locationPointId))){
      throw new ValidationException(new Throwable(ALIQUOT_NOT_IN_LOCATION), MaterialTrail.serializeToJsonString(materialTrail));
    } else if (materialTrail == null && !aliquot.getLocationPoint().equals(new ObjectId(locationPointId))){
      throw new ValidationException(new Throwable(ALIQUOT_NOT_IN_LOCATION), Aliquot.serialize(aliquot));
    }
    return aliquot;
  }

  @Override
  public Aliquot find(String code) throws DataNotFoundException {
    return aliquotDao.find(code);
  }

  @Override
  public List<Aliquot> getAliquotsByPeriod(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO, String locationPointId) throws DataNotFoundException {
    List<String> aliquotsOfLocationPoint = aliquotDao.getAliquotsByOrigin(locationPointId);
    List<String> aliquotsNotInOrigin = materialTrackingDao.verifyIfAliquotsAreInOrigin(aliquotsOfLocationPoint, locationPointId);
    List<String> aliquotsInLocationPoint = materialTrackingDao.getAliquotsInLocation(locationPointId);
    return aliquotDao.getAliquotsByPeriod(transportationAliquotFiltersDTO, locationPointId, aliquotsInLocationPoint, aliquotsNotInOrigin);
  }

  @Override
  public boolean exists(String code) {
    return aliquotDao.exists(code);
  }

  @Override
  public List<Aliquot> getExamLotAliquots(ObjectId lotOId) {
    return aliquotDao.getExamLotAliquots(lotOId);
  }

}
