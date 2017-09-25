package br.org.otus.laboratory.project.transportation.persistence;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface TransportationLotDao {

	void persist(TransportationLot transportationLot);

	TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException;

	List<TransportationLot> find();

	void delete(String id) throws DataNotFoundException;

	List<TransportationAliquot> getAliquots() throws DataNotFoundException;

}
