package org.ccem.otus.service.extraction.model;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.navigation.enums.NavigationTrackingItemStatuses;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.participant.model.Participant;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityExtractionActivityData {

  private String activityId;
  private String acronym;
  private Integer version;
  private Long recruitmentNumber;
  @SerializedName("recruitment_number")
  private String recruitmentNumberStr;
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

  public ActivityExtractionActivityData(SurveyActivity surveyActivity) {
    this.activityId = surveyActivity.getActivityID().toHexString();
    this.acronym = surveyActivity.getSurveyForm().getAcronym();
    this.version = surveyActivity.getSurveyForm().getVersion();
    this.mode = surveyActivity.getMode().toString();
    this.type = "";
    this.category = surveyActivity.getCategory().getName();
    this.activityFieldCenter = surveyActivity.getParticipantData().getFieldCenter().getAcronym();

    surveyActivity.getLastInterview().ifPresent(interview -> {
      this.interviewer = interview.getInterviewer().getEmail();
    });

    surveyActivity.getCurrentStatus().ifPresent(status -> {
      this.currentStatus = status.getName();
      this.currentStatusDate = status.getDate().toString();
    });

    this.creationDate = surveyActivity.getCreationStatus().getDate().toString();

    if(surveyActivity.getMode() == ActivityMode.PAPER){
      surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).ifPresent(status -> {
        this.paperInterviewer = status.getUser().getEmail();
        this.paperRealizationDate = status.getDate().toString();
      });
    }

    this.externalId = surveyActivity.getExternalID();
    this.fillingList = surveyActivity.getFillContainer().getFillingList();

    this.navigationTrackingItems = surveyActivity.getNavigationTracker().items
      .stream().filter(item -> item.getState().equals(String.valueOf(NavigationTrackingItemStatuses.SKIPPED)))
      .collect(Collectors.toList());

    this.recruitmentNumber = surveyActivity.getParticipantData().getRecruitmentNumber();
    this.recruitmentNumberStr = this.recruitmentNumber.toString();
  }


  public String getId() {
    return activityId;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public void setParticipantData(Participant participant){
    this.participantFieldCenter = participant.getFieldCenter().getAcronym();
  }
}
