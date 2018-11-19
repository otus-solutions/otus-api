package br.org.otus.laboratory.project.exam.examLot.validators;

import java.util.Iterator;
import java.util.List;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

public class ExamLotValidator {

	private ExamLot examLot;
	private ExamLotDao examLotDao;
	private AliquotDao aliquotDao;
	private TransportationLotDao transportationLotDao;
	private ExamLotValidationResult examLotValidationResult;

	public ExamLotValidator(ExamLotDao examLotDao, TransportationLotDao transportationLotDao, ExamLot examLot, AliquotDao aliquotDao) {
		this.examLotDao = examLotDao;
		this.transportationLotDao = transportationLotDao;
		this.aliquotDao = aliquotDao;
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
		List<Aliquot> aliquotListInDB = aliquotDao.getAliquots();
		examLot.getAliquotList().forEach( AliquotInLot -> {
			boolean contains = false;
			for (Aliquot aliquotInDB : aliquotListInDB) {
				if (aliquotInDB.getCode().equals(AliquotInLot.getCode())) {
					contains = true;
					break;
				}
			}

			if (!contains) {
				examLotValidationResult.setValid(false);
				examLotValidationResult.pushConflict(AliquotInLot.getCode());
			}
		});
	}

	private void checkOfTypesInLot() {
		for (Aliquot aliquot : examLot.getAliquotList()) {
			if (!aliquot.getName().equals(examLot.getAliquotName())) {
				examLotValidationResult.setValid(false);
				break;
			}
		}
	}

	private void checkForAliquotsOnAnotherLots() {
		final List<ExamLot> examLotList = examLotDao.find();

		examLotList.remove(examLot);

		for (Aliquot aliquotInLot : examLot.getAliquotList()) {
			for (ExamLot examLotInDB : examLotList) {
				for (Aliquot aliquotInDB : examLotInDB.getAliquotList()) {
					if (aliquotInDB.getCode().equals(aliquotInLot.getCode())) {
						examLotValidationResult.setValid(false);
						examLotValidationResult.pushConflict(aliquotInLot.getCode());
						break;
					}
				}
			}
		}
	}

	private void checkOriginOfAliquots() {
		for (Aliquot aliquot : examLot.getAliquotList()) {
			if (!checkIfEqualsCenter(aliquot)) {
				if (!checkForAliquotsInLotOfTransport(aliquot)) {
					examLotValidationResult.setValid(false);
				}
			}
		}
	}

	private boolean checkForAliquotsInLotOfTransport(Aliquot aliquot) {
		List<TransportationLot> listOfTransportation = transportationLotDao.find();

		Iterator<TransportationLot> iterator = listOfTransportation.iterator();
		boolean exist = false;
		while (iterator.hasNext()) {
			TransportationLot transportationLot = iterator.next();
			for (Aliquot aliquotInTransport : aliquotDao.getAliquots()) {
				if (aliquotInTransport.getCode().equals(aliquot.getCode())) {
					exist = true;
					break;
				}
			}
		}
		return exist;
	}

	private boolean checkIfEqualsCenter(Aliquot aliquot) {
		if (aliquot.getFieldCenter().getAcronym().equals(examLot.getFieldCenter().getAcronym()))
			return true;
		return false;
	}

}
