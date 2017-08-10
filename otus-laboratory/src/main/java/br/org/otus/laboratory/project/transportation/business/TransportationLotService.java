package br.org.otus.laboratory.project.transportation.business;

import java.util.List;

import br.org.otus.laboratory.project.transportation.TransportationLot;

public interface TransportationLotService {
	
	TransportationLot create(TransportationLot transportationLot);
	
	TransportationLot update(TransportationLot transportationLot);
	
	List<TransportationLot> list (String fieldCenter);
	
	boolean delete(String id);
	
}
