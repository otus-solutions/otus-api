package br.org.otus.laboratory.project.transportation.persistence;

import java.util.List;

import br.org.otus.laboratory.project.transportation.TransportationLot;

public interface TransportationLotDao {

	void persist(TransportationLot transportationLot);	
	
	TransportationLot update(TransportationLot transportationLot);
	
	List<TransportationLot> find();
		
	void delete(String id);
}
