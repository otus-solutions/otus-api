package org.ccem.otus.model.survey.activity.user;

import br.org.otus.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
  @Mock
  private ActivityBasicUser basicUser;

  @Before
  public void setUp() {
    user = new User();

    basicUser = new ActivityBasicUser();
    basicUser.setName(NAME);
    basicUser.setSurname(SURNAME);
    basicUser.setEmail(EMAIL);
  }

  @Test
  public void method_createRevisionUser_should_call_ActivityBasicUser() {
    Whitebox.setInternalState(user, "name", NAME);
    Whitebox.setInternalState(user, "surname", SURNAME);
    Whitebox.setInternalState(user, "phone", PHONE);
    Whitebox.setInternalState(user, "email", EMAIL);

    assertEquals(activityBasicUserFactory.createRevisionUser(user).getName(), basicUser.getName());
    assertEquals(activityBasicUserFactory.createRevisionUser(user).getSurname(), basicUser.getSurname());
    assertEquals(activityBasicUserFactory.createRevisionUser(user).getEmail(), basicUser.getEmail());
    assertEquals(activityBasicUserFactory.createRevisionUser(user).getPhone(), null);
  }

}
