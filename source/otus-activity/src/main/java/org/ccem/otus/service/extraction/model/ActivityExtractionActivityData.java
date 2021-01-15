package org.ccem.otus.service.extraction.model;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.participant.model.Participant;

import java.util.List;

public class ActivityExtractionActivityData {

  @SerializedName("id")
  private String activityId;
  private String acronym;
  private Integer version;
  @SerializedName("recruitment_number")
  private String recruitmentNumber;
  @SerializedName("participant_field_center")
  private String participantFieldCenter;
  private String mode;
  private String type;
  private String category;
  @SerializedName("participant_field_center_by_activity")
  private String activityFieldCenter;
  private String interviewer;
  @SerializedName("current_status")
  private String currentStatus;
  @SerializedName("current_status_date")
  private String currentStatusDate;
  @SerializedName("creation_date")
  private String creationDate;
  @SerializedName("paper_realization_date")
  private String paperRealizationDate;
  @SerializedName("paper_interviewer")
  private String paperInterviewer;
  @SerializedName("last_finalization_date")
  private String lastFinalizationDate;
  @SerializedName("external_id")
  private String externalId;
  private List<QuestionFill> fillingList;
  private List<NavigationTrackingItem> navigationTrackingItems;


  public ActivityExtractionActivityData(SurveyActivity surveyActivity, Participant participant) {
    this.activityId = surveyActivity.getActivityID().toHexString();
    this.acronym = surveyActivity.getSurveyForm().getAcronym();
    this.version = surveyActivity.getSurveyForm().getVersion();
    this.recruitmentNumber = participant.getRecruitmentNumber().toString();
    this.participantFieldCenter = participant.getFieldCenter().getAcronym();
    this.mode = surveyActivity.getMode().toString();
    this.type = "";
    this.category = surveyActivity.getCategory().getName();
    this.activityFieldCenter = surveyActivity.getParticipantData().getFieldCenter().toString();

    surveyActivity.getLastInterview().ifPresent(interview -> {
      this.interviewer = interview.getInterviewer().getEmail();
    });

    setStatusInfo(surveyActivity);

    if(surveyActivity.getMode() == ActivityMode.PAPER){
      setPaperInfo(surveyActivity);
    }

    this.externalId = surveyActivity.getExternalID();
    this.fillingList = surveyActivity.getFillContainer().getFillingList();
    this.navigationTrackingItems = surveyActivity.getNavigationTracker().items;
  }


  public String getId() {
    return activityId;
  }

  private void setStatusInfo(SurveyActivity surveyActivity){
    surveyActivity.getCurrentStatus().ifPresent(status -> {
      this.currentStatus = status.getName();
      this.currentStatusDate = status.getDate().toString();
    });

    this.creationDate = surveyActivity.getCreationStatus().getDate().toString();
  }

  private void setPaperInfo(SurveyActivity surveyActivity){
    surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).ifPresent(status -> {
      this.paperInterviewer = status.getUser().getEmail();
      this.paperRealizationDate = status.getDate().toString();
    });
  }
}
