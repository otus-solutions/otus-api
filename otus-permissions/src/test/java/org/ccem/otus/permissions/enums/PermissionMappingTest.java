package org.ccem.otus.permissions.enums;

import static org.junit.Assert.assertTrue;

import org.ccem.otus.permissions.model.user.LaboratoryPermission;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class PermissionMappingTest {

  @Test
  public void method_getEnumByObjectType_should_return_expected_itemClass() throws Exception {
    assertTrue(PermissionMapping.getEnumByObjectType("SurveyGroupPermission").getItemClass() == SurveyGroupPermission.class);
    assertTrue(PermissionMapping.getEnumByObjectType("LaboratoryPermission").getItemClass() == LaboratoryPermission.class);
  }

  @Test(expected = RuntimeException.class)
  public void method_getEnumByObjectType_should_trow_RuntimeException() throws Exception {
    PermissionMapping.getEnumByObjectType("fakeDataSource");
  }

}
