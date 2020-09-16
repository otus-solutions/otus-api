package org.ccem.otus.model.survey.activity.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ActivityStatusOptionsTest {

  ActivityStatusOptions activityStatusOptions;

  @Test
  public void getName_method_should_return_content_o_the_enumeration() {
    assertEquals("CREATED", activityStatusOptions.CREATED.getName());
    assertEquals("INITIALIZED_OFFLINE", activityStatusOptions.INITIALIZED_OFFLINE.getName());
    assertEquals("INITIALIZED_ONLINE", activityStatusOptions.INITIALIZED_ONLINE.getName());
    assertEquals("OPENED", activityStatusOptions.OPENED.getName());
    assertEquals("SAVED", activityStatusOptions.SAVED.getName());
    assertEquals("FINALIZED", activityStatusOptions.FINALIZED.getName());
    assertEquals("REOPENED", activityStatusOptions.REOPENED.getName());
  }
}