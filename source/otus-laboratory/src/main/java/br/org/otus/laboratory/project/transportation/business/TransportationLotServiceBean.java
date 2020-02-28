package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.MaterialTrackingDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import br.org.otus.laboratory.project.transportation.validators.TransportationLotValidator;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TransportationLotServiceBean implements TransportationLotService {

  private static final String TRANSPORTATION = "transportation_lot";
  @Inject
  private TransportationLotDao transportationLotDao;
  @Inject
  private LaboratoryConfigurationDao laboratoryConfigurationDao;
  @Inject
  private AliquotDao aliquotDao;
  @Inject
  private MaterialTrackingDao materialTrackingDao;

  @Override
  public TransportationLot create(TransportationLot transportationLot, String userEmail, ObjectId userId) throws ValidationException, DataNotFoundException {
    _validateLot(transportationLot);

    transportationLot.setOperator(userEmail);
    transportationLot.setCode(laboratoryConfigurationDao.createNewLotCodeForTransportation(transportationLotDao.getLastTransportationLotCode()));

    ArrayList<String> aliquotCodeList = transportationLot.getAliquotCodeList();

    ObjectId transportationLotId = transportationLotDao.persist(transportationLot);
    transportationLot.setLotId(transportationLotId);
    if (!aliquotCodeList.isEmpty()) {
      aliquotDao.addToTransportationLot(aliquotCodeList, transportationLotId);
    }

    materialTrackingDao.updatePrevious(aliquotCodeList);

    ArrayList<Document> trails = aliquotDao.buildTrails(aliquotCodeList,userId,transportationLot);

    materialTrackingDao.insert(trails);
    return transportationLot;
  }

  @Override
  public TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException, ValidationException {
    _validateLot(transportationLot);
    TransportationLot oldTransportationLot = transportationLotDao.findByCode(transportationLot.getCode());
    aliquotDao.updateTransportationLotId(transportationLot.getAliquotCodeList(), oldTransportationLot.getLotId());
    TransportationLot updateResult = transportationLotDao.update(transportationLot);
    return updateResult;
  }

  @Override
  public List<TransportationLot> list(String locationPointId) {
    return transportationLotDao.findByLocationPoint(locationPointId);
  }

  @Override
  public void delete(String code) throws DataNotFoundException {
    Integer lastLotCode = transportationLotDao.getLastTransportationLotCode();

    if (laboratoryConfigurationDao.getLastInsertion(TRANSPORTATION) < lastLotCode) {
      laboratoryConfigurationDao.restoreLotConfiguration(TRANSPORTATION, lastLotCode);
    }

    TransportationLot transportationLot = transportationLotDao.findByCode(code);
    aliquotDao.updateTransportationLotId(new ArrayList<>(), (ObjectId) transportationLot.getLotId());

    transportationLotDao.delete(code);
  }

  private void _validateLot(TransportationLot transportationLot) throws ValidationException {
    TransportationLotValidator transportationLotValidator = new TransportationLotValidator(transportationLotDao, transportationLot);
    transportationLotValidator.validate();
  }
}
