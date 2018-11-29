package br.org.otus.laboratory.participant.aliquot.persistence;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public interface AliquotDao {

  List<Aliquot> getAliquots();

  void persist(Aliquot aliquot);

  List<Aliquot> list(Long recruitmentNumber);

  List<Aliquot> getAliquotsByPeriod(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO);

  void updateExamLotId(ArrayList<String> codeList, ObjectId loId) throws DataNotFoundException;

  Aliquot getAliquot(TransportationAliquotFiltersDTO workAliquotFiltersDTO) throws DataNotFoundException;
 	
  void addToTransportationLot(ArrayList<String> aliquotCodeList, ObjectId transportationLotId) throws DataNotFoundException;

  void updateTransportationLotId(ArrayList<String> codeList, ObjectId loId) throws DataNotFoundException;
	
  boolean exists(String code);

  void delete(String code) throws DataNotFoundException;

  Aliquot find(String code) throws DataNotFoundException;

  List<Aliquot> getExamLotAliquots(ObjectId lotOId);
}
