package org.ccem.otus.service.activityRevision;

import br.org.otus.model.User;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;

import java.util.List;

public interface ActivityRevisionService {

    void create(String activityRevisionJson, User user);

    List<ActivityRevision> getActivityRevisions(String activityID) throws DataNotFoundException;
}
