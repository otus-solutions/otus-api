package br.org.otus.survey.activity.activityReview;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import com.google.gson.JsonSyntaxException;
import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;
import org.ccem.otus.service.activityReview.ActivityReviewService;

import javax.inject.Inject;
import java.util.List;

public class ActivityReviewFacade {

	@Inject
	private ActivityReviewService activityReviewService;

	public String create(ActivityReview activityReview) {
		return activityReviewService.create(activityReview);
	}

	public ActivityReview deserialize(String activityReview) {
		try {
			return ActivityReview.deserialize(activityReview);
		} catch (JsonSyntaxException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public List<ActivityReview> list() {
		return activityReviewService.list();
	}
}
