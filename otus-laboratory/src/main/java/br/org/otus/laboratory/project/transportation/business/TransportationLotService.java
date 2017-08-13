package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface TransportationLotService {
	
	TransportationLot create(TransportationLot transportationLot);
	
	TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException;
	
	List<TransportationLot> list (String fieldCenter) ;
	
	void delete(String id) throws DataNotFoundException;

	List<TransportationAliquot> getAliquots() throws DataNotFoundException;
	
}
