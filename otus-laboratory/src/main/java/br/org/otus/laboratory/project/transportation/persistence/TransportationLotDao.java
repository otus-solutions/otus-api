package br.org.otus.laboratory.project.transportation.persistence;

import java.util.List;

import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.project.transportation.TransportationLot;

public interface TransportationLotDao {

	void persist(TransportationLot transportationLot);	
	
	TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException;
	
	List<TransportationLot> find();
		
	void delete(String id);

	List<TransportationAliquot> getAliquots() throws DataNotFoundException;

	}
