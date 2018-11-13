package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.TransportationLot;

import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface TransportationLotService {

	TransportationLot create(TransportationLot transportationLot, String email) throws ValidationException, DataNotFoundException;

	TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException, ValidationException;

	List<TransportationLot> list();

	void delete(String id) throws DataNotFoundException;
}
