package org.ccem.otus.service.activityRevision;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.user.ActivityBasicUser;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.persistence.ActivityRevisionDao;

import javax.inject.Inject;
import java.util.List;

public class ActivityRevisionServiceBean implements ActivityRevisionService {

    @Inject
    private ActivityRevisionDao activityRevisionDao;

    @Override
    public List<ActivityRevision> list(String activityId) throws DataNotFoundException {
        ObjectId activityOid = new ObjectId(activityId);
        return activityRevisionDao.find(activityOid);
    }

    @Override
    public void create(String activityRevisionJson, ActivityBasicUser user) {
        ActivityRevision revision = ActivityRevision.deserialize(activityRevisionJson);

        revision.setUser(user);

        activityRevisionDao.persist(revision);
    }
}
