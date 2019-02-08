package org.ccem.otus.model.survey.activity.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class ActivityBasicUserTest {
    private static final String NAME = "João";
    private static final String SURNAME = "Silva";
    private static final String PHONE = "51999999999";
    private static final String EMAIL = "otus@gmail.com";

    private ActivityBasicUser activityBasicUser;

    @Before
    public void setup(){
        activityBasicUser = new ActivityBasicUser();
        activityBasicUser.setName(NAME);
        activityBasicUser.setSurname(SURNAME);
        activityBasicUser.setPhone(PHONE);
        activityBasicUser.setEmail(EMAIL);
    }

    @Test
    public void getMethod_should_check_in() {
        assertEquals(NAME,activityBasicUser.getName());
        assertEquals(SURNAME,activityBasicUser.getSurname());
        assertEquals(PHONE,activityBasicUser.getPhone());
        assertEquals(EMAIL,activityBasicUser.getEmail());
    }
}
