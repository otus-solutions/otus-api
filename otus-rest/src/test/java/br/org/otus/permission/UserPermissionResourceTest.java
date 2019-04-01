package br.org.otus.permission;

import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.ccem.otus.permissions.persistence.user.UserPermissionDTO.deserializeSinglePermission;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserPermissionDTO.class})
public class UserPermissionResourceTest {

  private static final String EMAIL = "otus@gmail.com";
  private static final String OBJECT_TYPE = "SurveyGroupPermission";
  private static final String USER_PERMISSION_DTO_JSON = "{\"objectType\":\"SurveyGroupPermission\",\"email\":\"otus@otus.com\"}";
  private static final String EXPECTED_RESPONSE_LIST = "{\"data\":{\"permissions\":[{\"objectType\":\"SurveyGroupPermission\",\"email\":\"otus@otus.com\"}]}}";
  private static final String EXPECTED_RESPONSE_PERMISSION = "{\"data\":{\"objectType\":\"SurveyGroupPermission\",\"email\":\"otus@gmail.com\"}}";


  @InjectMocks
  private UserPermissionResource userPermissionResource;
  @Mock
  private PermissionFacade permissionFacade;
  private Permission permission;
  private UserPermissionDTO userPermissionDTO;

  @Before
  public void setUp() throws Exception {
    userPermissionDTO = deserializeSinglePermission(USER_PERMISSION_DTO_JSON);
    permission = new Permission();
    Whitebox.setInternalState(permission, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(permission, "email", EMAIL);
  }

  @Test
  public void getAllMethod_should_return_response_with_userPermissionList_by_email() {
    when(permissionFacade.getAll(EMAIL)).thenReturn(userPermissionDTO);
    assertEquals(EXPECTED_RESPONSE_LIST, userPermissionResource.getAll(EMAIL));
  }

  @Test
  public void savePermission() {
    mockStatic(UserPermissionDTO.class);
    UserPermissionDTO mockUserPermissionDTO = deserializeSinglePermission("");
    when(permissionFacade.savePermission(mockUserPermissionDTO)).thenReturn(permission);
    assertEquals(EXPECTED_RESPONSE_PERMISSION, userPermissionResource.savePermission(USER_PERMISSION_DTO_JSON));
  }
}