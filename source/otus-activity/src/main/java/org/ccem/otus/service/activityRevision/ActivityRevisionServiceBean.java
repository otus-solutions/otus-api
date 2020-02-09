package org.ccem.otus.service.activityRevision;

import br.org.otus.model.User;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.model.survey.activity.user.ActivityBasicUserFactory;
import org.ccem.otus.persistence.ActivityRevisionDao;

import javax.inject.Inject;
import java.util.List;

public class ActivityRevisionServiceBean implements ActivityRevisionService {

  @Inject
  private ActivityRevisionDao activityRevisionDao;

  @Override
  public List<ActivityRevision> getActivityRevisions(String activityID) throws DataNotFoundException {
    ObjectId activityOid = new ObjectId(activityID);
    return activityRevisionDao.findByActivityID(activityOid);
  }

  @Override
  public void create(String activityRevisionJson, User user) {
    ActivityRevision revision = ActivityRevision.deserialize(activityRevisionJson);
    ActivityBasicUserFactory activityBasicUserFactory = new ActivityBasicUserFactory();

    revision.setUser(activityBasicUserFactory.createRevisionUser(user));

    activityRevisionDao.persist(revision);
  }
}
