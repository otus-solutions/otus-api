package org.ccem.otus.model.survey.activity.user;

import br.org.otus.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class ActivityBasicUserFactoryTest {
    private static final String NAME = "João";
    private static final String SURNAME = "Silva";
    private static final String PHONE = "51999999999";
    private static final String EMAIL = "otus@gmail.com";

    @InjectMocks
    private ActivityBasicUserFactory activityBasicUserFactory;

    @Mock
    private User user;
    @Mock
    private ActivityBasicUser activityBasicUser;

    @Before
    public void setUp() {
        activityBasicUser = new ActivityBasicUser();
        user  = new User();

        activityBasicUser.setName(NAME);
        activityBasicUser.setSurname(SURNAME);
        activityBasicUser.setPhone(PHONE);
        activityBasicUser.setEmail(EMAIL);
    }

    @Test
    public void method_createRevisionUser_should_call_ActivityBasicUser() {
//        activityBasicUserFactory.createRevisionUser(user);
        Whitebox.setInternalState(user,"name",NAME);
        Whitebox.setInternalState(user,"surname",SURNAME);
        Whitebox.setInternalState(user,"phone",PHONE);
        Whitebox.setInternalState(user,"email",EMAIL);
        when(activityBasicUserFactory.createRevisionUser(user)).thenReturn(activityBasicUser);
//        assertEquals(,activityBasicUserFactory.createRevisionUser(user));
    }

}
