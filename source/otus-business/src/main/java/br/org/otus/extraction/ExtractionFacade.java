package br.org.otus.extraction;

import java.util.*;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.api.ExtractionService;
import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.extraction.ExamUploadExtration;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;
import br.org.otus.laboratory.extraction.LaboratoryExtraction;
import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;

public class ExtractionFacade {

  private final static Logger LOGGER = Logger.getLogger("br.org.otus.extraction.ExtractionFacade");

  @Inject
  private ExamUploadFacade examUploadFacade;
  @Inject
  private ParticipantLaboratoryFacade participantLaboratoryFacade;
  @Inject
  private ExtractionService extractionService;


  public byte[] createLaboratoryExamsValuesExtraction() {
    LinkedList<ParticipantExamUploadResultExtraction> records = examUploadFacade.getExamResultsExtractionValues();
    ExamUploadExtration extractor = new ExamUploadExtration(records);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build("Results to extraction not found."));
    }
  }

  public byte[] createLaboratoryExtraction() {
    LinkedList<LaboratoryRecordExtraction> extraction = participantLaboratoryFacade.getLaboratoryExtraction();
    LaboratoryExtraction extractor = new LaboratoryExtraction(extraction);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build("Results to extraction not found."));
    }
  }

}
