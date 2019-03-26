package org.ccem.otus.permissions.enums;

import org.ccem.otus.permissions.model.user.SurveyGroupPermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class PermissionMappingTest {

  @Test
  public void method_getEnumByObjectType_should_return_ParticipantDataSource() throws Exception {
    assertTrue(PermissionMapping.getEnumByObjectType("SurveyGroupPermission").getItemClass() == SurveyGroupPermission.class);
  }

  @Test(expected=RuntimeException.class)
  public void method_getEnumByObjectType_should_trow_RuntimeException() throws Exception {
    PermissionMapping.getEnumByObjectType("fakeDataSource");
  }

}
