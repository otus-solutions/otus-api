package br.org.otus.laboratory.project.transportation.persistence;

import br.org.otus.laboratory.project.transportation.MaterialTrail;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public interface MaterialTrackingDao {

  MaterialTrail getCurrent (String materialCode);

  List<String> getAliquotsInLocation(String locationPointId);

  void insert(ArrayList<Document> trails);

  void updatePrevious(ArrayList<String> aliquotCodeList);
}
