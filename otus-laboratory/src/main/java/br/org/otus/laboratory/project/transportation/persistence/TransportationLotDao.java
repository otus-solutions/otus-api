package br.org.otus.laboratory.project.transportation.persistence;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.TransportationLot;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;
import java.util.Set;

public interface TransportationLotDao {

	void persist(TransportationLot transportationLot);

	TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException;

	List<TransportationLot> find();

	void delete(String id) throws DataNotFoundException;

	List<WorkAliquot> getAliquots() throws DataNotFoundException;

	Set<String> getAliquotsDescriptorsInTransportationLots() throws DataNotFoundException;
}
