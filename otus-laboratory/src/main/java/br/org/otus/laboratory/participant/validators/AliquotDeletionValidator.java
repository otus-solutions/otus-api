package br.org.otus.laboratory.participant.validators;

import org.ccem.otus.exceptions.webservice.validation.DeletionException;

import br.org.otus.laboratory.project.exam.persistence.ExamLotDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

public class AliquotDeletionValidator {

  private String code;
  private ExamLotDao examLotDao;
  private TransportationLotDao transportationLotDao;
  private AliquotDeletionValidatorResult aliquotDeletionValidatorResult;

  public AliquotDeletionValidator(String code, ExamLotDao examLotDao, TransportationLotDao transportationLotDao) {
    this.code = code;
    this.examLotDao = examLotDao;
    this.transportationLotDao = transportationLotDao;
    this.aliquotDeletionValidatorResult = new AliquotDeletionValidatorResult();
  }

  public void validate() throws DeletionException {
    this.aliquotInTransportation();
    this.aliquotInExamLot();
    this.aliquotInExamResult();
  }

  private void aliquotInTransportation() {
    String transported = this.transportationLotDao.checkIfThereInTransport(code);
    if (transported != null)
      this.aliquotDeletionValidatorResult.setTransportationLot(transported);
  }

  private void aliquotInExamLot() {
    String examLot = this.examLotDao.checkIfThereInExamLot(code);
    if (examLot != null)
      this.aliquotDeletionValidatorResult.setExamLot(examLot);
  }

  private void aliquotInExamResult() {
    // TODO:
  }

}
