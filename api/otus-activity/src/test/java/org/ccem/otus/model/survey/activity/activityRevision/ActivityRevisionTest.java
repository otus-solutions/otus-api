package org.ccem.otus.model.survey.activity.activityRevision;

import org.ccem.otus.model.survey.activity.user.ActivityBasicUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ActivityRevisionTest {

    private ActivityRevision activityRevision;
    private String activityRevisionJson;

    @Mock
    private ActivityBasicUser activityBasicUser;

    @Before
    public void setUp() {
        activityRevision = new ActivityRevision();
        activityRevisionJson = ActivityRevision.serialize(activityRevision);
    }

    @Test
    public void setUserMethod_should_check_in() {
        activityRevision.setUser(activityBasicUser);
        assertEquals(activityBasicUser,activityRevision.getUser());
    }

    @Test
    public void serializeStaticMethod_should_convert_objectModel_to_JsonString() {
        assertTrue(ActivityRevision.serialize(activityRevision) instanceof String);
    }

    @Test
    public void deserializeStaticMethod_shold_convert_JsonString_to_objectModel() {
        assertTrue(ActivityRevision.deserialize(activityRevisionJson) instanceof ActivityRevision);
    }
}
