package org.ccem.otus.model.survey.activity;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.FillContainer;
import org.ccem.otus.model.survey.activity.interview.Interview;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.navigation.NavigationTracker;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.utils.AnswerAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class SurveyActivity {

	private String objectType;
	@SerializedName("_id")
	private ObjectId activityID;
	private SurveyForm surveyForm;
	private ActivityMode mode;
	private ActivityCategory category;
	private Participant participantData;
	private List<Interview> interviews;
	private FillContainer fillContainer;
	private List<ActivityStatus> statusHistory;
	private Boolean isDiscarded;
	private NavigationTracker navigationTracker;

	public SurveyActivity() {
		this.isDiscarded = Boolean.FALSE;
	}

	public void setIsDiscarded(Boolean isDiscarded) {
		this.isDiscarded = isDiscarded;
	}

	public Boolean isDiscarded() {
		return isDiscarded;
	}

	public ObjectId getActivityID() {
		return activityID;
	}

	public String getObjectType() {
		return objectType;
	}

	public SurveyForm getSurveyForm() {
		return surveyForm;
	}

	public ActivityMode getMode() {
		return mode;
	}

	public List<Interview> getInterviews() {
		return interviews;
	}

	public FillContainer getFillContainer() {
		return fillContainer;
	}

	public List<ActivityStatus> getStatusHistory() {
		return statusHistory;
	}

	public Participant getParticipantData() {
		return participantData;
	}

	public NavigationTracker getNavigationTracker() {
		return navigationTracker;
	}

	public Optional<ActivityStatus> getCurrentStatus() {
		return this.statusHistory.stream().reduce((activityStatus, activityStatus2) -> activityStatus2);
	}

	public Optional<ActivityStatus> getLastStatusByName(String name) {
		return statusHistory.stream()
				.filter(status -> status.getName().equals(name))
				.reduce((activityStatus, activityStatus2) -> activityStatus2);
	}

	public Optional<Interview> getLastInterview(){
		return this.interviews.stream().reduce((interview, interview2) -> interview2);

	}

	public static String serialize(SurveyActivity surveyActivity) {
		return getGsonBuilder().create().toJson(surveyActivity);
	}

	public static SurveyActivity deserialize(String surveyActivity) {
		return getGsonBuilder().create().fromJson(surveyActivity, SurveyActivity.class);
	}

	/**
	 * @return a GsonBuilder instance with AnswerAdapter, ObjectIdAdapter
	 *         registered and also all registered adapters of SurveyForm.
	 *         {@link SurveyForm#getGsonBuilder}
	 * 
	 */
	public static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = SurveyForm.getGsonBuilder();

		builder.registerTypeAdapter(AnswerFill.class, new AnswerAdapter());
		builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
		builder.serializeNulls();

		return builder;
	}
}
