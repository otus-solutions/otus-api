package org.ccem.otus.service.activityReview;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;
import org.ccem.otus.persistence.ActivityReviewDao;

import javax.inject.Inject;
import java.util.List;

public class ActivityReviewServiceBean implements ActivityReviewService{

    @Inject
    private ActivityReviewDao activityReviewDao;

    @Override
    public List<ActivityReview> list() {
        return activityReviewDao.find();
    }

    @Override
    public String create(ActivityReview activityReview) {
        ObjectId objectId = activityReviewDao.persist(activityReview);
        return objectId.toString();
    }
}
