package br.org.otus.laboratory.project.transportation.validarors;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;
import java.util.Optional;

public class TransportationLotValidator {

	private TransportationLotDao transportationLotDao;
	private TransportationLot transportationLot;
	private TransportationLotValidationResult transportationLotValidationResult;

	public TransportationLotValidator(TransportationLotDao transportationLotDao, TransportationLot transportationLot) {
		this.transportationLotDao = transportationLotDao;
		this.transportationLot = transportationLot;
		this.transportationLotValidationResult = new TransportationLotValidationResult();
	}

	public void validate() throws ValidationException, DataNotFoundException {
		checkForAliquotsOnAnotherLots();
		if (!transportationLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("There are aliquots in another lot."),
					transportationLotValidationResult);
		}

		checkIfAliquotsExist();
		if (!transportationLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("Aliquots not found"),
					transportationLotValidationResult);
		}
	}

	private void checkForAliquotsOnAnotherLots() {
		final List<TransportationLot> transportationLotList = transportationLotDao.find();
		transportationLot.getAliquotList().forEach(transportationAliquot -> {
			Optional<TransportationLot> searchedAliquot = transportationLotList.stream().filter(transportationLot1 -> {
				return transportationLot1.getAliquotList().contains(transportationAliquot);
			}).findFirst();

			if (searchedAliquot.isPresent()) {
				transportationLotValidationResult.setValid(false);
				transportationLotValidationResult.pushConflict(transportationAliquot.getCode());
			}
		});
	}

	private void checkIfAliquotsExist() throws DataNotFoundException {
		final List<TransportationAliquot> aliquotList = transportationLotDao.getAliquots();
		transportationLot.getAliquotList().forEach(transportationAliquot -> {
			boolean contains = aliquotList.contains(transportationAliquot);

			if (!contains) {
				transportationLotValidationResult.setValid(false);
				transportationLotValidationResult.pushConflict(transportationAliquot.getCode());
			}
		});
	}
}
