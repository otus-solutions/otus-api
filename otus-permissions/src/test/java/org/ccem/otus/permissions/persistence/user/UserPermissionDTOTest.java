package org.ccem.otus.permissions.persistence.user;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.permissions.model.user.Permission;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class UserPermissionDTOTest {

  private UserPermissionDTO userPermissionDTO;
  private ArrayList<Permission> permissions;
  private Permission permission;

  @Before
  public void setup() {
    this.userPermissionDTO = new UserPermissionDTO();
    this.permissions = new ArrayList<Permission>();

    this.permission = new Permission();
    Whitebox.setInternalState(this.permission, "objectType", "Permission");
    Whitebox.setInternalState(this.permission, "email", "test1@test");
    this.permissions.add(this.permission);

    Whitebox.setInternalState(this.userPermissionDTO, "permissions", permissions);
  }

  @Test
  public void concatenatePermissions_method_should_return_concatenated_permissions() {
    UserPermissionDTO other = new UserPermissionDTO();
    ArrayList<Permission> otherPermissions = new ArrayList<Permission>();
    Permission otherPermission = new Permission();
    Whitebox.setInternalState(otherPermission, "objectType", "Permission");
    Whitebox.setInternalState(otherPermission, "email", "test2@test");
    otherPermissions.add(otherPermission);
    Whitebox.setInternalState(other, "permissions", otherPermissions);

    this.userPermissionDTO.concatenatePermissions(other);
    Assert.assertEquals(2, this.userPermissionDTO.getPermissions().size());
    Assert.assertEquals("test1@test", this.userPermissionDTO.getPermissions().get(0).getEmail());
    Assert.assertEquals("test2@test", this.userPermissionDTO.getPermissions().get(1).getEmail());
  }

  @Test
  public void concatenatePermissions_method_does_should_not_concatenated_when_permission_is_duplicate() {
    UserPermissionDTO other = new UserPermissionDTO();
    ArrayList<Permission> otherPermissions = new ArrayList<Permission>();
    Permission otherPermission = new Permission();
    Whitebox.setInternalState(otherPermission, "objectType", "Permission");
    Whitebox.setInternalState(otherPermission, "email", "test1@test");
    otherPermissions.add(otherPermission);
    Whitebox.setInternalState(other, "permissions", otherPermissions);

    this.userPermissionDTO.concatenatePermissions(other);
    Assert.assertEquals(1, this.userPermissionDTO.getPermissions().size());
    Assert.assertEquals("test1@test", this.userPermissionDTO.getPermissions().get(0).getEmail());
  }

  @Ignore
  @Test
  public void deserialize_method_should_return_expected_UserPermissionDTO_with_elements() {
    String serialized = UserPermissionDTO.serialize(this.userPermissionDTO);
    UserPermissionDTO deserialized = UserPermissionDTO.deserialize(serialized);

    Assert.assertThat(deserialized, CoreMatchers.instanceOf(UserPermissionDTO.class));
    Assert.assertEquals("test1@test", deserialized.getPermissions().get(0).getEmail());
  }

  @Ignore
  @Test
  public void deserializeSinglePermission_method_should_return_expected_UserPermissionDTO_with_elements() {
    String serialized = UserPermissionDTO.serialize(this.userPermissionDTO);
    UserPermissionDTO deserialized = UserPermissionDTO.deserializeSinglePermission(serialized);

    Assert.assertThat(deserialized, CoreMatchers.instanceOf(UserPermissionDTO.class));
    Assert.assertEquals("test1@test", deserialized.getPermissions().get(0).getEmail());
  }

  @Test
  public void getPermissions_method_should_return_expected_permissions() {
    List<Permission> permissions = this.userPermissionDTO.getPermissions();

    Assert.assertEquals("Permission", permissions.get(0).getObjectType());
    Assert.assertEquals("test1@test", permissions.get(0).getEmail());
  }

}
