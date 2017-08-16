package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface TransportationLotService {
	
	TransportationLot create(TransportationLot transportationLot) throws ValidationException;
	
	TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException;

	List<TransportationLot> list();

	void delete(String id) throws DataNotFoundException;

	List<TransportationAliquot> getAliquots() throws DataNotFoundException;
	
}
