package br.org.otus.laboratory.project.transportation.persistence;

import br.org.otus.laboratory.project.transportation.MaterialTrail;
import br.org.otus.laboratory.project.transportation.TrailHistoryRecord;
import br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.model.MaterialTrackingResultExtraction;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface MaterialTrackingDao {

  MaterialTrail getCurrent(String materialCode);

  List<String> getAliquotsInLocation(String locationPointId);

  void insert(ArrayList<Document> trails);

  void updatePrevious(List<String> aliquotCodeList);

  ArrayList<String> verifyNeedToRollback(List<String> removedAliquotCodes, ObjectId transportationLotId);

  ArrayList<ObjectId> getLastTrailsToRollBack(ArrayList<String> materialsToRollBack);

  void activateTrails(ArrayList<ObjectId> trailsToActivate);

  void removeTransportation(ObjectId transportationLotId);

  void removeMaterialTransportation(ObjectId transportationLotId, ArrayList<String> aliquotsToRollBack);

  ArrayList<String> verifyNeedToRollback(ObjectId lotId);

  void insert(MaterialTrail materialTrail);

  void setReceived(MaterialTrail materialTrail);

  List<String> verifyIfAliquotsAreInOrigin(List<String> aliquotsOfLocationPoint, String locationPointId);

  List<TrailHistoryRecord> getMaterialTrackingList(String materialCode) throws DataNotFoundException;

  MaterialTrail getTrail(String materialCode, ObjectId transportationLotId);

  LinkedList<MaterialTrackingResultExtraction> getMaterialTrackingExtraction() throws DataNotFoundException;
}
