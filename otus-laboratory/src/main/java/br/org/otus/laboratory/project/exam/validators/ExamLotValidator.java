package br.org.otus.laboratory.project.exam.validators;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
		checkIfAliquotsExist();
		if (!examLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("Aliquots not found"), examLotValidationResult);
		}

		checkOfTypesInLot();
		if (!examLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("There are different types of aliquots in lot"),
					examLotValidationResult);
		}

		checkForAliquotsOnAnotherLots();
		if (!examLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("There are aliquots in another lot"), examLotValidationResult);
		}

		checkOriginOfAliquots();
		if (!examLotValidationResult.isValid()) {
			throw new ValidationException(
					new Throwable("There are aliquots different from the center and not exist in lot of transport"),
					examLotValidationResult);
		}
	}

	private void checkIfAliquotsExist() {
		try {
			List<WorkAliquot> workAliquotList;
			workAliquotList = examLotDao.getAliquots();
			examLot.getAliquotList().forEach(workAliquot -> {
				boolean contains = workAliquotList.contains(workAliquot);

				if (!contains) {
					examLotValidationResult.setValid(false);
				}
			});
		} catch (DataNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void checkOfTypesInLot() {
		for (WorkAliquot workAliquot : examLot.getAliquotList()) {
			if (!workAliquot.getName().equals(examLot.getAliquotName())) {
				examLotValidationResult.setValid(false);
				break;
			}
		}
	}

	private void checkForAliquotsOnAnotherLots() {
		final List<ExamLot> examLotList = examLotDao.find();

		for (ExamLot current : examLotList) {
			if (current.getCode().equals(this.examLot.getCode())) {
				examLotList.remove(current);
				break;
			}
		}

		examLot.getAliquotList().forEach(workAliquot -> {
			Optional<ExamLot> searchedAliquot = examLotList.stream().filter(lot -> {
				return lot.getAliquotList().contains(workAliquot);
			}).findFirst();

			if (searchedAliquot.isPresent()) {
				examLotValidationResult.setValid(false);
				examLotValidationResult.pushConflict(workAliquot.getCode());
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
		while (iterator.hasNext()) {
			TransportationLot transportationLot = iterator.next();
			if (transportationLot.getAliquotList().contains(aliquot))
				exist = true;
		}
		return exist;
	}

	private boolean checkIfEqualsCenter(WorkAliquot aliquot) {
		if (aliquot.getFieldCenter().getAcronym().equals(examLot.getFieldCenter().getAcronym()))
			return true;
		return false;
	}

}
