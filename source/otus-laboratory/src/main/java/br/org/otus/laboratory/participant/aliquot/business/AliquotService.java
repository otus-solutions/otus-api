package br.org.otus.laboratory.participant.aliquot.business;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface AliquotService {

  List<Aliquot> getAliquots();

  List<Aliquot> getAliquots(Long rn);

  Aliquot getAliquot(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO, String locationPointId) throws ValidationException, DataNotFoundException;

  Aliquot find(String code) throws DataNotFoundException;

  List<Aliquot> getAliquotsByPeriod(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO, String locationPointId) throws DataNotFoundException;

  boolean exists(String code);

  List<Aliquot> getExamLotAliquots(ObjectId lotOId);
}
