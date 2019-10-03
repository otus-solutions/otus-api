package org.ccem.otus.permissions.model.user;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class SurveyGroupPermissionTest {

  private static final String OBJECT_SERIALIZED = "{\"objectType\":\"Permission\",\"email\":\"test@test\"}";
  private static final String OBJECT_DESERIALIZED = "{\"groups\":[\"CSJ\",\"MED\"]}";
  private SurveyGroupPermission surveyGroupPermission;
  private Permission permission;
  private Set<String> groups;

  @Before
  public void setup() {
    this.groups = new HashSet<String>();
    this.groups.add("CSJ");
    this.groups.add("MED");
    this.surveyGroupPermission = new SurveyGroupPermission();
    Whitebox.setInternalState(this.surveyGroupPermission, "groups", groups);

    this.permission = new Permission();
    Whitebox.setInternalState(this.permission, "objectType", "Permission");
    Whitebox.setInternalState(this.permission, "email", "test@test");
  }

  @Test
  public void serialize_method_should_return_expected_string_with_elements() {
    String serialized = SurveyGroupPermission.serialize(this.permission);

    Assert.assertThat(serialized, CoreMatchers.containsString(OBJECT_SERIALIZED));
  }

  @Test
  public void deserialize_method_should_return_expected_SurveyGroupPermission_with_elements() {
    SurveyGroupPermission deserialized = SurveyGroupPermission.deserialize(OBJECT_DESERIALIZED);

    Assert.assertThat(deserialized, CoreMatchers.instanceOf(SurveyGroupPermission.class));
    Assert.assertTrue(deserialized.getGroups().contains("CSJ"));
    Assert.assertTrue(deserialized.getGroups().contains("MED"));
  }

  @Test
  public void equals_method_should_return_true_when_is_same_instance() {

    Assert.assertTrue(this.surveyGroupPermission.equals(this.surveyGroupPermission));
  }

  @Test
  public void equals_method_should_return_false_when_is_not_same_instance() {
    SurveyGroupPermission other = new SurveyGroupPermission();
    Set<String> otherGroups = new HashSet<String>();
    Whitebox.setInternalState(other, "groups", otherGroups);

    Assert.assertFalse(this.surveyGroupPermission.equals(other));
  }

}
