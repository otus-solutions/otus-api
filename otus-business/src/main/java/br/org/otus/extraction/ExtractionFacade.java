package br.org.otus.extraction;

import br.org.otus.api.ExtractionService;
import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.extraction.ExamUploadExtration;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;
import br.org.otus.fileuploader.api.FileUploaderFacade;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.download.ZipBuilder;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.survey.form.SurveyForm;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExtractionFacade {

  @Inject
  private ActivityFacade activityFacade;

  @Inject
  private SurveyFacade surveyFacade;

  @Inject
  private ExtractionService extractionService;

  @Inject
  private ExamUploadFacade examUploadFacade;

  @Inject
  private FileUploaderFacade fileUploaderFacade;

  public byte[] createActivityExtraction(String acronym, Integer version) throws DataNotFoundException {
    List<SurveyActivity> activities = activityFacade.get(acronym, version);

    SurveyForm surveyForm = surveyFacade.get(acronym, version);
    SurveyActivityExtraction extractor = new SurveyActivityExtraction(surveyForm, activities);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new DataNotFoundException(new Throwable("RESULTS TO EXTRACTION {" + acronym + "} not found."));
    }
  }

  public List<Integer> listSurveyVersions(String acronym) {
    return surveyFacade.listVersions(acronym);
  }


  public byte[] createLaboratoryExamsValuesExtraction() throws DataNotFoundException {
    LinkedList<ParticipantExamUploadResultExtraction> records = examUploadFacade.getExamResultsExtractionValues();
    ExamUploadExtration extractor = new ExamUploadExtration(records);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new DataNotFoundException(new Throwable("results to extraction not found."));
    }
  }

  public byte[] createAttachmentsReportExtraction(String acronym, Integer version) {
    try {
      return extractionService.getAttachmentsReport(acronym, version);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Extraction.NotFound.build(e.getMessage()));
    }
  }

  public byte[] downloadFiles(ArrayList<String> oids) {
    return fileUploaderFacade.downloadFiles(oids);
  }
}
