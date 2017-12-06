package br.org.otus.laboratory.project.exam.validators;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.ExamLot;
import br.org.otus.laboratory.project.exam.persistence.ExamLotDao;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

public class ExamLotValidator {

	private ExamLot examLot;
	private ExamLotDao examLotDao;
	private TransportationLotDao transportationLotDao;
	private ExamLotValidationResult examLotValidationResult;

	public ExamLotValidator(ExamLotDao examLotDao, TransportationLotDao transportationLotDao, ExamLot examLot) {
		this.examLotDao = examLotDao;
		this.transportationLotDao = transportationLotDao;
		this.examLot = examLot;
		this.examLotValidationResult = new ExamLotValidationResult();
	}

	public void validate() throws ValidationException {
		checkForAliquotsOnAnotherLots();
		if (!examLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("There are aliquots in another lot."), examLotValidationResult);
		}

		checkOriginOfAliquots();
		if (!examLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("There are aliquots different from the center and not exist in lot of transport."), examLotValidationResult);
		}
	}

	private void checkForAliquotsOnAnotherLots() {
		final List<ExamLot> examLotList = examLotDao.find();

		examLotList.remove(examLot);

		examLot.getAliquotList().forEach(examAliquot -> {
			Optional<ExamLot> searchedAliquot = examLotList.stream().filter(lot -> {
				return lot.getAliquotList().contains(examAliquot);
			}).findFirst();

			if (searchedAliquot.isPresent()) {
				examLotValidationResult.setValid(false);
				examLotValidationResult.pushConflict(examAliquot.getCode());
			}
		});
	}

	private void checkOriginOfAliquots() {
		for (WorkAliquot aliquot : examLot.getAliquotList()) {
			if (!checkIfEqualsCenter(aliquot)) {
				if (!checkForAliquotsInLotOfTransport(aliquot)) {
					examLotValidationResult.setValid(false);
				}
			}
		}
	}

	private boolean checkForAliquotsInLotOfTransport(WorkAliquot aliquot) {
		List<TransportationLot> listOfTransportation = transportationLotDao.find();

		Iterator<TransportationLot> iterator = listOfTransportation.iterator();
		boolean exist = false;
		while (iterator.hasNext() || !exist) {
			TransportationLot transportationLot = iterator.next();
			if (transportationLot.getAliquotList().contains(aliquot))
				exist = true;
		}
		return true;
	}

	private boolean checkIfEqualsCenter(WorkAliquot aliquot) {
		if (!aliquot.getFieldCenter().equals(examLot.getFieldCenter()))
			return false;
		return true;
	}
}
