package br.org.otus.laboratory.participant.validators;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;
import br.org.otus.laboratory.project.transportation.MaterialTrail;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import br.org.otus.laboratory.project.transportation.persistence.MaterialTrackingDao;

public class AliquotDeletionValidator {

  private Aliquot aliquotToValidate;
  private String code;
  private ExamLotDao examLotDao;
  private TransportationLotDao transportationLotDao;
  private AliquotDao aliquotDao;
  private ExamUploader examUploader;
  private AliquotDeletionValidatorResponse aliquotDeletionValidatorResponse;
  private MaterialTrackingDao materialTrackingDao;

  public AliquotDeletionValidator(String code, AliquotDao aliquotDao, ExamUploader examUploader, ExamLotDao examLotDao, TransportationLotDao transportationLotDao) {
    this.code = code;
    this.aliquotDao = aliquotDao;
    this.examUploader = examUploader;
    this.examLotDao = examLotDao;
    this.transportationLotDao = transportationLotDao;
    this.aliquotDeletionValidatorResponse = new AliquotDeletionValidatorResponse();
  }

  public void validate() throws ValidationException, DataNotFoundException {
    aliquotToValidate = aliquotDao.find(this.code);
    this.aliquotInTransportation();
    this.aliquotInExamLot();
    this.aliquotInExamResult();
    this.aliquotInReceivedMaterials();
    if (!this.aliquotDeletionValidatorResponse.isDeletionValidated()) {
      throw new ValidationException(new Throwable("Exclusion of unauthorized aliquot."), this.aliquotDeletionValidatorResponse);
    }
  }

  private void aliquotInTransportation() {
    ObjectId transportationLotId = this.aliquotToValidate.getTransportationLotId();
    if (transportationLotId != null) {
      try {
        this.aliquotDeletionValidatorResponse.setTransportationLot(transportationLotDao.find(transportationLotId).getCode());
      } catch (DataNotFoundException e) {
        this.aliquotDeletionValidatorResponse.setTransportationLot("Not Found");
      }
      this.aliquotDeletionValidatorResponse.setDeletionValidated(Boolean.FALSE);
    }
  }

  private void aliquotInExamLot() {
    ObjectId examLotId = this.aliquotToValidate.getExamLotId();
    if (examLotId != null) {
      try {
        this.aliquotDeletionValidatorResponse.setExamLot(examLotDao.find(examLotId).getCode());
      } catch (DataNotFoundException e) {
        this.aliquotDeletionValidatorResponse.setExamLot("Not Found");
      }
      this.aliquotDeletionValidatorResponse.setDeletionValidated(Boolean.FALSE);
    }
  }

  private void aliquotInExamResult() {
    Boolean examResult = this.examUploader.checkIfThereInExamResultLot(this.code);
    if (examResult) {
      this.aliquotDeletionValidatorResponse.setExamResult(Boolean.TRUE);
      this.aliquotDeletionValidatorResponse.setDeletionValidated(Boolean.FALSE);
    }
  }

  private void aliquotInReceivedMaterials() {
    MaterialTrail materialTrail = materialTrackingDao.getCurrent(this.code);

    if (materialTrail.getReceived()) {
      this.aliquotDeletionValidatorResponse.setReceivedMaterial(Boolean.TRUE);
      this.aliquotDeletionValidatorResponse.setDeletionValidated(Boolean.FALSE);
    }
  }

}
