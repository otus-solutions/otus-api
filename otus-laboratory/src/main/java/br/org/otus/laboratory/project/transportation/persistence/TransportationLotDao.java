package br.org.otus.laboratory.project.transportation.persistence;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.TransportationLot;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.HashSet;
import java.util.List;

public interface TransportationLotDao {

	void persist(TransportationLot transportationLot);

	TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException;

	List<TransportationLot> find();

	void delete(String id) throws DataNotFoundException;

	List<WorkAliquot> getAliquots() throws DataNotFoundException;

	List<WorkAliquot> getAliquotsByPeriod(WorkAliquotFiltersDTO workAliquotFiltersDTO) throws DataNotFoundException;

	HashSet<Document> getAliquotsInfoInTransportationLots() throws DataNotFoundException;

	WorkAliquot getAliquot(WorkAliquotFiltersDTO workAliquotFiltersDTO);

	
}
