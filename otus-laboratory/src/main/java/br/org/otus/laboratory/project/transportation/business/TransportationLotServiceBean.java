package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import br.org.otus.laboratory.project.transportation.validarors.TransportationLotValidator;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TransportationLotServiceBean implements TransportationLotService {

	@Inject
	private TransportationLotDao transportationLotDao;

	@Override
	public TransportationLot create(TransportationLot transportationLot, String email) throws ValidationException, DataNotFoundException {
		_validateLot(transportationLot);
		transportationLot.setOperator(email);
		transportationLotDao.persist(transportationLot);
		return transportationLot;
	}

	@Override
	public TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException, ValidationException {
		_validateLot(transportationLot);
		TransportationLot updateResult = transportationLotDao.update(transportationLot);
		return updateResult;

	}

	@Override
	public List<TransportationLot> list() {
		return transportationLotDao.find();
	}

	@Override
	public void delete(String id) throws DataNotFoundException {
		transportationLotDao.delete(id);
	}

	@Override
	public List<TransportationAliquot> getAliquots() throws DataNotFoundException {
		return transportationLotDao.getAliquots();
	}

	private void _validateLot(TransportationLot transportationLot) throws ValidationException {
		TransportationLotValidator transportationLotValidator = new TransportationLotValidator(transportationLotDao, transportationLot);
		transportationLotValidator.validate();
	}


}
