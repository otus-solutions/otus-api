package org.ccem.otus.service.activityRevision;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.persistence.ActivityRevisionDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @Before
    public void setup() {
        objectId = new ObjectId(ACTIVITY_ID);
        when(activityRevisionDao.persist(revision)).thenReturn(objectId);
        ArrayList<ActivityRevision> activities = new ArrayList<ActivityRevision>();
        activities.add(revision);
        when(activityRevisionDao.find(objectId)).thenReturn(activities);
    }

    @Test
    public void method_create_should_call_ActivityRevisionDao_persist() {
        activityRevisionService.create(revision);
        verify(activityRevisionDao, times(1)).persist(revision);
    }
    @Test
    public void listMethod_should_invoke_find_of_ActivityDao_find() {
        activitiesRevision = activityRevisionService.list(objectId);
        assertTrue(activitiesRevision.get(0) instanceof ActivityRevision);
        verify(activityRevisionDao, times(1)).find(objectId);
    }
}
