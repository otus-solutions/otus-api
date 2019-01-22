package org.ccem.otus.service.activityRevision;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;

import java.util.List;

public interface ActivityRevisionService {

    String create(ActivityRevision activityRevision);

    List<ActivityRevision> list(ObjectId activityId);
}
