package org.ccem.otus.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;

public interface ActivityRevisionDao {

    void persist(ActivityRevision activityRevision);

    List<ActivityRevision> find(ObjectId activityId) throws DataNotFoundException;
}
