package org.ccem.otus.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;

public interface ActivityRevisionDao {

    ObjectId persist(ActivityRevision activityRevision);

    List<ActivityRevision> find(ObjectId activityId);
}
