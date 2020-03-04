package br.org.otus.laboratory.project.transportation.persistence;

import br.org.otus.laboratory.project.transportation.MaterialTrail;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public interface MaterialTrackingDao {

  MaterialTrail getCurrent (String materialCode);

  List<String> getAliquotsInLocation(String locationPointId);

  void insert(ArrayList<Document> trails);

  void updatePrevious(ArrayList<String> aliquotCodeList);

  ArrayList<String> verifyNeedToRollback(ArrayList<String> removedAliquotCodes, ObjectId transportationLotId);

  ArrayList<ObjectId> getLastTrailsToRollBack(ArrayList<String> materialsToRollBack);

  void activateTrails(ArrayList<ObjectId> trailsToActivate);

  void removeTransportation(ObjectId transportationLotId);

  ArrayList<String> verifyNeedToRollback(ObjectId lotId);

  void insert(MaterialTrail materialTrail);

  List<String> verifyIfAliquotsAreInOrigin(List<String> aliquotsOfLocationPoint, String locationPointId);
}
