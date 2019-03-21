package org.ccem.otus.permissions.persistence.user;

import java.util.ArrayList;

import org.ccem.otus.permissions.model.user.Permission;
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

	// TODO:
	@Ignore
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
	}

	@Test
	public void deserialize_method_should_return_expected_UserPermissionDTO_with_elements() {
		// TODO:
	}

	@Test
	public void getPermissions_method_should_return_expected_permissions() {
		ArrayList<Permission> permissions = this.userPermissionDTO.getPermissions();

		Assert.assertEquals("Permission", permissions.get(0).getObjectType());
		Assert.assertEquals("test1@test", permissions.get(0).getEmail());
	}

}
