package br.org.otus.laboratory.project.exam.examLot.validators;

import java.util.ArrayList;
import java.util.List;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.exam.examLot.ExamLot;

public class ExamLotValidator {

  private ExamLot examLot;
  private AliquotDao aliquotDao;
  private ExamLotValidationResult examLotValidationResult;

  public ExamLotValidator(ExamLot examLot, AliquotDao aliquotDao) {
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
    List<Aliquot> aliquotsRenewed = new ArrayList<>();
    examLot.getAliquotList().forEach(AliquotInLot -> {
      boolean contains = false;
      try {
        Aliquot aliquotFound = aliquotDao.find(AliquotInLot.getCode());
        aliquotsRenewed.add(aliquotFound);
        contains = true;
      } catch (DataNotFoundException e) {
        examLotValidationResult.setValid(false);
        examLotValidationResult.pushConflict(AliquotInLot.getCode());
      }

      if (!contains) {
        examLotValidationResult.setValid(false);
        examLotValidationResult.pushConflict(AliquotInLot.getCode());
      } else {
        examLot.setAliquotList(aliquotsRenewed);
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
    for (Aliquot aliquotInLot : examLot.getAliquotList()) {
      if (aliquotInLot.getExamLotId() != null) {
        examLotValidationResult.setValid(false);
        examLotValidationResult.pushConflict(aliquotInLot.getCode());
        break;
      }
    }
  }

  private void checkOriginOfAliquots() {
    for (Aliquot aliquot : examLot.getAliquotList()) {
      if (!checkIfEqualsCenter(aliquot) && aliquot.getTransportationLotId() == null) {
        examLotValidationResult.setValid(false);
      }
    }
  }

  private boolean checkIfEqualsCenter(Aliquot aliquot) {
    return aliquot.getFieldCenter().getAcronym().equals(examLot.getFieldCenter().getAcronym());
  }

}
