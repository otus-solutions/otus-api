package br.org.otus.permission;

import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.service.user.UserPermissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.ccem.otus.permissions.persistence.user.UserPermissionDTO.deserializeSinglePermission;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class PermissionFacadeTest {
  private static final String EMAIL = "otus@gmail.com";
  private static final String OBJECT_TYPE = "SurveyGroupPermission";
  private static final String USER_PERMISSION_DTO_JSON = "{\"objectType\":\"SurveyGroupPermission\",\"email\":\"otus@otus.com\"}";


  @InjectMocks
  private PermissionFacade permissionFacade;
  @Mock
  private UserPermissionService userPermissionService;
  private Permission permission;
  private UserPermissionDTO userPermissionDTO;
  private Exception exception;
  private DataNotFoundException dataNotFoundException;

  @Before
  public void setUp() {
    userPermissionDTO = deserializeSinglePermission(USER_PERMISSION_DTO_JSON);
    permission = new Permission();
    Whitebox.setInternalState(permission, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(permission, "email", EMAIL);
    exception = spy(new Exception());
    dataNotFoundException = spy(new DataNotFoundException());
  }

  @Test
  public void getAllMethod_should_return_userPermissionDto() throws Exception {
    when(userPermissionService.getAll(EMAIL)).thenReturn(userPermissionDTO);
    assertTrue(permissionFacade.getAll(EMAIL) instanceof UserPermissionDTO);
  }

  @Test(expected = HttpResponseException.class)
  public void getAllMethod_should_capture_generic_exception() throws Exception {
    when(userPermissionService.getAll(EMAIL)).thenThrow(exception);
    permissionFacade.getAll(EMAIL);
  }

  @Test
  public void savePermissionMethod_should_return_permission_in_case_of_successful_persistence() throws DataNotFoundException {
    when(userPermissionService.savePermission(anyObject())).thenReturn(permission);
    assertTrue(permissionFacade.savePermission(userPermissionDTO) instanceof Permission);
  }

  @Test(expected = HttpResponseException.class)
  public void savePermissionMethod_should_capture_DataNotFoundException() throws DataNotFoundException {
    when(userPermissionService.savePermission(anyObject())).thenThrow(dataNotFoundException);
    permissionFacade.savePermission(userPermissionDTO);
  }
}