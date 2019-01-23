package org.ccem.otus.service.activityRevision;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.persistence.ActivityRevisionDao;
import org.ccem.otus.model.survey.activity.user.ActivityBasicUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class ActivityRevisionServiceBeanTest {
    private static final String ACTIVITY_ID = "5c41c6b316da48006573a169";
    private static final String ACTIVITY_REVISION_JSON = "{\"activityId\" : \"5c41c6b316da48006573a169\"," + "\"reviewDate\" : \"17/01/2019\"}";

    @InjectMocks
    private ActivityRevisionServiceBean activityRevisionService;
    @Mock
    private ActivityRevisionDao activityRevisionDao;
    @Mock
    private ActivityRevision activityRevision;
    @Mock
    private ActivityRevision revision;
    private ObjectId objectId;
    @Mock
    private List<ActivityRevision> activitiesRevision;
    @Mock
    private ActivityBasicUser user;

    @Before
    public void setup() throws DataNotFoundException {
        objectId = new ObjectId(ACTIVITY_ID);
        ArrayList<ActivityRevision> activities = new ArrayList<ActivityRevision>();
        activities.add(revision);
        when(activityRevisionDao.find(objectId)).thenReturn(activities);
    }

    @Test
    public void method_create_should_call_ActivityRevisionDao_persist() {
        activityRevisionService.create(ACTIVITY_REVISION_JSON, user);
        verify(activityRevisionDao, times(1)).persist(Mockito.any());
    }
    @Test
    public void listMethod_should_invoke_find_of_ActivityRevisionDao_find() throws DataNotFoundException {
        activitiesRevision = activityRevisionService.list(ACTIVITY_ID);
        assertTrue(activitiesRevision.get(0) instanceof ActivityRevision);
        verify(activityRevisionDao, times(1)).find(objectId);
    }
}
