package org.ccem.otus.permissions.model.user;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class LaboratoryPermissionTest {

  private static final String OBJECT_SERIALIZED = "{\"objectType\":\"Permission\",\"email\":\"test@test\"}";
  private static final String OBJECT_DESERIALIZED = "{\"participantLaboratoryAccess\": false, \"sampleTransportationAccess\":false,\"examLotsAccess\":false,\"examSendingAccess\":false,\"unattachedLaboratoriesAccess\":false, \"laboratoryMaterialManagerAccess\":false, \"aliquotManagerAccess\":false}";
  private LaboratoryPermission laboratoryPermission;
  private Permission permission;

  @Before
  public void setup() {
    this.laboratoryPermission = new LaboratoryPermission();
    Whitebox.setInternalState(this.laboratoryPermission, "participantLaboratoryAccess", Boolean.FALSE);
    Whitebox.setInternalState(this.laboratoryPermission, "sampleTransportationAccess", Boolean.FALSE);
    Whitebox.setInternalState(this.laboratoryPermission, "examLotsAccess", Boolean.FALSE);
    Whitebox.setInternalState(this.laboratoryPermission, "examSendingAccess", Boolean.FALSE);
    Whitebox.setInternalState(this.laboratoryPermission, "unattachedLaboratoriesAccess", Boolean.FALSE);
    Whitebox.setInternalState(this.laboratoryPermission, "laboratoryMaterialManagerAccess", Boolean.FALSE);
    Whitebox.setInternalState(this.laboratoryPermission, "aliquotManagerAccess", Boolean.FALSE);

    this.permission = new Permission();
    Whitebox.setInternalState(this.permission, "objectType", "Permission");
    Whitebox.setInternalState(this.permission, "email", "test@test");
  }

  @Test
  public void serialize_method_should_return_expected_string_with_elements() {
    String serialized = LaboratoryPermission.serialize(this.permission);

    Assert.assertThat(serialized, CoreMatchers.containsString(OBJECT_SERIALIZED));
  }

  @Test
  public void deserialize_method_should_return_expected_LaboratoryPermission_with_elements() {
    LaboratoryPermission deserialized = LaboratoryPermission.deserialize(OBJECT_DESERIALIZED);

    Assert.assertThat(deserialized, CoreMatchers.instanceOf(LaboratoryPermission.class));
    Assert.assertTrue(deserialized.getSampleTransportationAccess().equals(Boolean.FALSE));
    Assert.assertTrue(deserialized.getExamSendingAccess().equals(Boolean.FALSE));
    Assert.assertTrue(deserialized.getExamLotsAccess().equals(Boolean.FALSE));
    Assert.assertTrue(deserialized.getUnattachedLaboratoriesAccess().equals(Boolean.FALSE));
    Assert.assertTrue(deserialized.getLaboratoryMaterialManagerAccess().equals(Boolean.FALSE));
    Assert.assertTrue(deserialized.getAliquotManagerAccess().equals(Boolean.FALSE));
  }

  @Test
  public void equals_method_should_return_true_when_is_same_instance() {

    Assert.assertTrue(this.laboratoryPermission.equals(this.laboratoryPermission));
  }

}
