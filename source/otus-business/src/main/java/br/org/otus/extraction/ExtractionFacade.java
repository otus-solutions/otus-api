package br.org.otus.extraction;

import java.net.MalformedURLException;
import java.util.*;

import javax.inject.Inject;

import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.response.info.Validation;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.DataSourceService;
import org.ccem.otus.service.extraction.ActivityProgressExtraction;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.service.extraction.factories.ActivityProgressRecordsFactory;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;
import org.ccem.otus.service.extraction.preprocessing.AutocompleteQuestionPreProcessor;
import org.ccem.otus.survey.form.SurveyForm;

import br.org.otus.api.ExtractionService;
import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.extraction.ExamUploadExtration;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;
import br.org.otus.fileuploader.api.FileUploaderFacade;
import br.org.otus.laboratory.extraction.LaboratoryExtraction;
import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;

public class ExtractionFacade {

  @Inject
  private ActivityFacade activityFacade;
  @Inject
  private SurveyFacade surveyFacade;
  @Inject
  private ExamUploadFacade examUploadFacade;
  @Inject
  private AutocompleteQuestionPreProcessor autocompleteQuestionPreProcessor;
  @Inject
  private ParticipantLaboratoryFacade participantLaboratoryFacade;
  @Inject
  private FileUploaderFacade fileUploaderFacade;
  @Inject
  private ExtractionService extractionService;
  @Inject
  private DataSourceService dataSourceService;

  public byte[] createActivityExtraction(String acronym, Integer version) throws DataNotFoundException {
    SurveyForm surveyForm = surveyFacade.get(acronym, version);
    List<SurveyActivity> activities = activityFacade.getExtraction(acronym, version);
    Map<Long, String> fieldCenterByRecruitmentNumber = activityFacade.getParticipantFieldCenterByActivity(acronym, version);

    dataSourceService.populateDataSourceMapping();
    SurveyActivityExtraction extractor = new SurveyActivityExtraction(surveyForm, activities, fieldCenterByRecruitmentNumber);
    extractor.addPreProcessor(autocompleteQuestionPreProcessor);

    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new DataNotFoundException(new Throwable("RESULTS TO EXTRACTION {" + acronym + "} not found."));
    }
  }

  public byte[] createExtractionFromPipeline(String pipelineName) {
    try {
      GatewayResponse gatewayResponse = new ExtractionGatewayService().getPipelineExtraction(pipelineName);
      return (byte[]) gatewayResponse.getData();
    } catch (MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
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

  public byte[] createLaboratoryExtraction() throws DataNotFoundException {
    LinkedList<LaboratoryRecordExtraction> extraction = participantLaboratoryFacade.getLaboratoryExtraction();
    LaboratoryExtraction extractor = new LaboratoryExtraction(extraction);
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
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public byte[] createActivityProgressExtraction(String center) {
    LinkedList<ActivityProgressResultExtraction> progress = activityFacade.getActivityProgressExtraction(center);
    ActivityProgressRecordsFactory extraction = new ActivityProgressRecordsFactory(progress);
    ActivityProgressExtraction extractor = new ActivityProgressExtraction(extraction);
    try {
      return extractionService.createExtraction(extractor);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public byte[] downloadFiles(ArrayList<String> oids) {
    return fileUploaderFacade.downloadFiles(oids);
  }

}
