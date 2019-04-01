package org.ccem.otus.permissions.service.user;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.persistence.user.UserPermissionGenericDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class UserPermissionServiceBeanTest {
  private static final String EMAIL = "otus@gmail.com";

  @InjectMocks
  private UserPermissionServiceBean userPermissionServiceBean;
  @Mock
  private UserPermissionGenericDao userPermissionGenericDao;
  @Mock
  private Permission permission;

  @Test
  public void getAllMethod_should_invoke_getUserPermissions_of_userPermissionGenericDao() throws Exception {
    userPermissionServiceBean.getAll(EMAIL);
    verify(userPermissionGenericDao, times(1)).getUserPermissions(EMAIL);
  }

  @Test
  public void savePermission_should_invoke_savePermission_of_userPermissionGenericDao() throws DataNotFoundException {
    userPermissionServiceBean.savePermission(permission);
    verify(userPermissionGenericDao, times(1)).savePermission(permission);
  }

  @Test
  public void getUserPermittedSurveys_should_invoke_getUserPermittedSurveys_of_userPermissionGenericDao() {
    userPermissionServiceBean.getUserPermittedSurveys(EMAIL);
    verify(userPermissionGenericDao, times(1)).getUserPermittedSurveys(EMAIL);
  }
}