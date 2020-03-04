package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.project.transportation.MaterialTrail;
import br.org.otus.laboratory.project.transportation.TransportMaterialCorrelation;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.MaterialTrackingDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportMaterialCorrelationDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
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
  @Inject
  private TransportMaterialCorrelationDao transportMaterialCorrelationDao;
  @Inject
  private ParticipantLaboratoryDao participantLaboratoryDao;

  @Override
  public TransportationLot create(TransportationLot transportationLot, String userEmail, ObjectId userId) throws ValidationException, DataNotFoundException {
    transportationLot.setOperator(userEmail);
    transportationLot.setCode(laboratoryConfigurationDao.createNewLotCodeForTransportation(transportationLotDao.getLastTransportationLotCode()));

    ArrayList<String> aliquotCodeList = transportationLot.getAliquotCodeList();

    ArrayList<String> tubeCodeList = transportationLot.getTubeCodeList();

    ObjectId transportationLotId = transportationLotDao.persist(transportationLot);

    TransportMaterialCorrelation transportMaterialCorrelation = new TransportMaterialCorrelation(transportationLotId, aliquotCodeList, tubeCodeList);
    transportMaterialCorrelationDao.persist(transportMaterialCorrelation);

    transportationLot.setLotId(transportationLotId);
    if (!aliquotCodeList.isEmpty()) {
      aliquotDao.addToTransportationLot(aliquotCodeList, transportationLotId);
    }

    materialTrackingDao.updatePrevious(aliquotCodeList);
    materialTrackingDao.updatePrevious(tubeCodeList);

    ArrayList<Document> trails = aliquotDao.buildTrails(aliquotCodeList,userId,transportationLot);
    if(trails != null){
      materialTrackingDao.insert(trails);
    }

    tubeCodeList.forEach(tubeCode -> {
      MaterialTrail materialTrail = new MaterialTrail(userId,tubeCode,transportationLot);
      materialTrackingDao.insert(materialTrail);
    });

    return transportationLot;
  }

  @Override
  public TransportationLot update(TransportationLot transportationLot, ObjectId userId) throws DataNotFoundException, ValidationException {
    ArrayList<String> currentAliquotCodeList = transportationLot.getAliquotCodeList();
    ArrayList<String> currentTubeCodeList = transportationLot.getTubeCodeList();
    TransportMaterialCorrelation transportMaterialCorrelation = transportMaterialCorrelationDao.get(transportationLot.getLotId());

    ArrayList<String> removedMaterialCodes = transportMaterialCorrelation.getRemovedAliquots(currentAliquotCodeList);
    removedMaterialCodes.addAll(transportMaterialCorrelation.getRemovedTubes(currentTubeCodeList));

    ArrayList<String> newMaterialCodes = transportMaterialCorrelation.getNewAliquots(currentAliquotCodeList);
    newMaterialCodes.addAll(transportMaterialCorrelation.getNewTubes(currentAliquotCodeList));

    rollBackMaterial(transportationLot, removedMaterialCodes);
    materialTrackingDao.updatePrevious(newMaterialCodes);
    createNewTrails(userId, transportationLot, newMaterialCodes);

    TransportationLot oldTransportationLot = transportationLotDao.findByCode(transportationLot.getCode());
    aliquotDao.updateTransportationLotId(currentAliquotCodeList, oldTransportationLot.getLotId());
    TransportationLot updateResult = transportationLotDao.update(transportationLot);
    transportMaterialCorrelationDao.update(transportationLot.getLotId(), currentAliquotCodeList, currentTubeCodeList);
    return updateResult;
  }

  private void createNewTrails(ObjectId userId, TransportationLot transportationLot, ArrayList<String> newMaterialCodes) {
    if(newMaterialCodes != null){
      newMaterialCodes.forEach(materialCode -> {
        MaterialTrail materialTrail = new MaterialTrail(userId,materialCode,transportationLot);
        materialTrackingDao.insert(materialTrail);
      });
    }
  }

  private void rollBackMaterial(TransportationLot transportationLot, ArrayList<String> removedMaterialCodes) {
    if(removedMaterialCodes != null){
      ArrayList<String> aliquotsToRollBack = materialTrackingDao.verifyNeedToRollback(removedMaterialCodes,transportationLot.getLotId());
      materialTrackingDao.removeTransportation(transportationLot.getLotId());
      if (aliquotsToRollBack.size() > 0){
        ArrayList<ObjectId> TrailsToActivate = materialTrackingDao.getLastTrailsToRollBack(aliquotsToRollBack);
        materialTrackingDao.activateTrails(TrailsToActivate);
      }
    }
  }

  @Override
  public List<TransportationLot> list(String locationPointId) {
    List<TransportationLot> transportationLots = transportationLotDao.findByLocationPoint(locationPointId);
    transportationLots.forEach(lot -> {
      TransportMaterialCorrelation transportMaterialCorrelation = transportMaterialCorrelationDao.get(lot.getLotId());
      ArrayList<Aliquot> aliquots = aliquotDao.getAliquots(transportMaterialCorrelation.getAliquotCodeList());
      ArrayList<Tube> tubes = participantLaboratoryDao.getTubes(transportMaterialCorrelation.getTubeCodeList());
      lot.setAliquotList(aliquots);
      lot.setTubeList(tubes);
    });
    return transportationLots;
  }

  @Override
  public void delete(String code) throws DataNotFoundException {
    Integer lastLotCode = transportationLotDao.getLastTransportationLotCode();

    if (laboratoryConfigurationDao.getLastInsertion(TRANSPORTATION) < lastLotCode) {
      laboratoryConfigurationDao.restoreLotConfiguration(TRANSPORTATION, lastLotCode);
    }

    TransportationLot transportationLot = transportationLotDao.findByCode(code);
    aliquotDao.updateTransportationLotId(new ArrayList<>(), (ObjectId) transportationLot.getLotId());
    ArrayList<String> materialsToRollBack = materialTrackingDao.verifyNeedToRollback(transportationLot.getLotId());
    materialTrackingDao.removeTransportation(transportationLot.getLotId());
    if (materialsToRollBack.size() > 0){
      ArrayList<ObjectId> TrailsToActivate = materialTrackingDao.getLastTrailsToRollBack(materialsToRollBack);
      materialTrackingDao.activateTrails(TrailsToActivate);
    }
    transportationLotDao.delete(code);
  }
}
