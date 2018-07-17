package br.org.otus.laboratory.participant.validators;

import org.ccem.otus.exceptions.webservice.validation.DeletionException;

import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

public class AliquotDeletionValidator {

  private String code;
  private ExamLotDao examLotDao;
  private TransportationLotDao transportationLotDao;
  private ExamUploader examUploader;
  private AliquotDeletionValidatorResponse aliquotDeletionValidatorResponse;

  public AliquotDeletionValidator(String code, ExamLotDao examLotDao, TransportationLotDao transportationLotDao, ExamUploader examUploader) {
    this.code = code;
    this.examLotDao = examLotDao;
    this.transportationLotDao = transportationLotDao;
    this.examUploader = examUploader;
    this.aliquotDeletionValidatorResponse = new AliquotDeletionValidatorResponse();
  }

  public void validate() throws DeletionException {
    this.aliquotInTransportation();
    this.aliquotInExamLot();
    this.aliquotInExamResult();
    if (!this.aliquotDeletionValidatorResponse.isDeletionValidated()) {
      throw new DeletionException(new Throwable("Exclusion of unauthorized aliquot."), this.aliquotDeletionValidatorResponse);
    }
  }

  private void aliquotInTransportation() {
    String transported = this.transportationLotDao.checkIfThereInTransport(this.code);
    if (transported != null) {
      this.aliquotDeletionValidatorResponse.setTransportationLot(transported);
      this.aliquotDeletionValidatorResponse.setDeletionValidated(Boolean.FALSE);
    }
  }

  private void aliquotInExamLot() {
    String examLot = this.examLotDao.checkIfThereInExamLot(this.code);
    if (examLot != null) {
      this.aliquotDeletionValidatorResponse.setExamLot(examLot);
      this.aliquotDeletionValidatorResponse.setDeletionValidated(Boolean.FALSE);
    }
  }

  private void aliquotInExamResult() {
    String examResult = this.examUploader.checkIfThereInExamResultLot(this.code);
    if (examResult != null) {
      this.aliquotDeletionValidatorResponse.setExamResult(Boolean.TRUE);
      this.aliquotDeletionValidatorResponse.setDeletionValidated(Boolean.FALSE);
    }
  }

}
