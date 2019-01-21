package org.ccem.otus.service.activityReview;

import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;

import java.util.List;

public interface ActivityReviewService {

    String create(ActivityReview activityReview);

    List<ActivityReview> list();
}
