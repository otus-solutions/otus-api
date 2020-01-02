package org.ccem.otus.service.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionHeadersFactory;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionRecordsFactory;
import org.ccem.otus.service.extraction.preprocessing.ActivityPreProcessor;
import org.ccem.otus.survey.form.SurveyForm;

import br.org.otus.api.Extractable;

public class SurveyActivityExtraction implements Extractable {

  private List<SurveyActivity> surveyActivities;
  private SurveyForm surveyForm;
  private SurveyActivityExtractionHeadersFactory headersFactory;
  private SurveyActivityExtractionRecordsFactory recordsFactory;
  private List<ActivityPreProcessor> processors;
  private HashMap<Long, String> fieldCenterByRecruitmentNumber;

  public SurveyActivityExtraction(SurveyForm surveyForm, List<SurveyActivity> surveyActivities, HashMap<Long, String> fieldCenterByRecruitmentNumber) {
    this.processors = new ArrayList<>();
    this.surveyActivities = surveyActivities;
    this.surveyForm = surveyForm;
    this.headersFactory = new SurveyActivityExtractionHeadersFactory(this.surveyForm);
    this.fieldCenterByRecruitmentNumber = fieldCenterByRecruitmentNumber;
  }

  public List<String> getHeaders() {
    return this.headersFactory.getHeaders();
  }

  public List<List<Object>> getValues() throws DataNotFoundException {
    List<List<Object>> values = new ArrayList<>();

    for (SurveyActivity surveyActivity : this.surveyActivities) {
      List<Object> resultInformation = new ArrayList<>();
      String participantFieldCenter = getParticipantFieldCenterByRecruitmentNumber(surveyActivity.getParticipantData().getRecruitmentNumber());
      this.recordsFactory = new SurveyActivityExtractionRecordsFactory(this.surveyForm, this.headersFactory.getHeaders());
      this.recordsFactory.buildSurveyBasicInfo(surveyActivity, participantFieldCenter);
      this.recordsFactory.buildSurveyQuestionInfo(surveyActivity);
      for (ActivityPreProcessor preprocessor : this.processors) {
        try {
          this.recordsFactory = preprocessor.process(this.surveyForm, this.recordsFactory);
        } catch (DataNotFoundException e) {
          throw new DataNotFoundException(new Throwable(e.getMessage()));
        }
      }
      resultInformation.addAll(new ArrayList<>(this.recordsFactory.getSurveyInformation().values()));
      values.add(resultInformation);
    }
    return values;
  }

  public void addPreProcessor(ActivityPreProcessor activityPreProcessor) {
    this.processors.add(activityPreProcessor);
  }

  private String getParticipantFieldCenterByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    return fieldCenterByRecruitmentNumber.get(recruitmentNumber);
  }

}
