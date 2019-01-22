package org.ccem.otus.service.activityRevision;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.persistence.ActivityRevisionDao;

import javax.inject.Inject;
import java.util.List;

public class ActivityRevisionServiceBean implements ActivityRevisionService {

    @Inject
    private ActivityRevisionDao activityRevisionDao;

    @Override
    public List<ActivityRevision> list(ObjectId activityId) {
        return activityRevisionDao.find(activityId);
    }

    @Override
    public String create(ActivityRevision activityRevision) {
        ObjectId objectId = activityRevisionDao.persist(activityRevision);
        return objectId.toString();
    }
}
