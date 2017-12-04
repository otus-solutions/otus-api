package br.org.otus.laboratory.project.exam.validators;

import java.util.List;
import java.util.Optional;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.ExamLot;
import br.org.otus.laboratory.project.exam.persistence.ExamLotDao;

public class ExamLotValidator {

	private ExamLotDao examLotDao;
	private ExamLot examLot;
	private ExamLotValidationResult examLotValidationResult;

	public ExamLotValidator(ExamLotDao examLotDao, ExamLot examLot) {
		this.examLotDao = examLotDao;
		this.examLot = examLot;
		this.examLotValidationResult = new ExamLotValidationResult();
	}

	public void validate() throws ValidationException {
		checkForAliquotsOnAnotherLots();
		if (!examLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("There are aliquots in another lot."), examLotValidationResult);
		}

		// checkIfAliquotsExist();
		if (!examLotValidationResult.isValid()) {
			throw new ValidationException(new Throwable("Aliquots not found"), examLotValidationResult);
		}
	}

	private void checkForAliquotsOnAnotherLots() {
		final List<ExamLot> examLotList = examLotDao.find();

		examLotList.remove(examLot);

		examLot.getAliquotList().forEach(examAliquot -> {
			Optional<ExamLot> searchedAliquot = examLotList.stream().filter(transportationLot1 -> {
				return transportationLot1.getAliquotList().contains(examAliquot);
			}).findFirst();

			if (searchedAliquot.isPresent()) {
				examLotValidationResult.setValid(false);
				examLotValidationResult.pushConflict(examAliquot.getCode());
			}
		});
	}

	private void checkIfAliquotsExist() throws DataNotFoundException {
		final List<WorkAliquot> aliquotList = examLotDao.getAliquots();
		examLot.getAliquotList().forEach(transportationAliquot -> {
			boolean contains = aliquotList.contains(transportationAliquot);

			if (!contains) {
				examLotValidationResult.setValid(false);
				examLotValidationResult.pushConflict(transportationAliquot.getCode());
			}
		});
	}
}
