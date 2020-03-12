package br.org.otus.persistence.pendency.dto;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserActivityPendencyFieldOrderingOptionsTest {

  @Test
  public void contains_static_method_should_return_TRUE_for_each_enum_value(){
    assertTrue(UserActivityPendencyFieldOrderingOptions.contains(UserActivityPendencyFieldOrderingOptions.STATUS.getName()));
    assertTrue(UserActivityPendencyFieldOrderingOptions.contains(UserActivityPendencyFieldOrderingOptions.ACRONYM.getName()));
    assertTrue(UserActivityPendencyFieldOrderingOptions.contains(UserActivityPendencyFieldOrderingOptions.RECRUITMENT_NUMBER.getName()));
    assertTrue(UserActivityPendencyFieldOrderingOptions.contains(UserActivityPendencyFieldOrderingOptions.EXTERNAL_ID.getName()));
    assertTrue(UserActivityPendencyFieldOrderingOptions.contains(UserActivityPendencyFieldOrderingOptions.DUE_DATE.getName()));
    assertTrue(UserActivityPendencyFieldOrderingOptions.contains(UserActivityPendencyFieldOrderingOptions.REQUESTER.getName()));
    assertTrue(UserActivityPendencyFieldOrderingOptions.contains(UserActivityPendencyFieldOrderingOptions.RECEIVER.getName()));
  }

  @Test
  public void contains_static_method_should_return_FALSE_for_invalid_value(){
    assertFalse(UserActivityPendencyFieldOrderingOptions.contains(""));
  }

}
