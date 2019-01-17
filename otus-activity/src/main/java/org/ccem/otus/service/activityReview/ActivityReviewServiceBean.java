package org.ccem.otus.service.activityReview;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;
import org.ccem.otus.persistence.ActivityReviewDao;

import javax.inject.Inject;

public class ActivityReviewServiceBean implements ActivityReviewService{

    @Inject
    private ActivityReviewDao activityReviewDao;

    @Override
    public String create(ActivityReview activityReview) {
        ObjectId objectId = activityReviewDao.persist(activityReview);
        return objectId.toString();
    }
}
