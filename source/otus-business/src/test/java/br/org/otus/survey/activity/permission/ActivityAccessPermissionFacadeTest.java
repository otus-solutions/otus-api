package br.org.otus.survey.activity.permission;

import static org.mockito.Mockito.verify;

import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
public class ActivityAccessPermissionFacadeTest {

  @InjectMocks
  private ActivityAccessPermissionFacade facade;
  @Mock
  private ActivityAccessPermissionService activityAccessPermissionService;
  @Mock
  private ActivityAccessPermission permission;

  @Test
  public void createMethod_should_invoke_create_of_activityAccessPermissionService() {
    facade.create(permission);
    verify(activityAccessPermissionService, Mockito.times(1)).create(permission);
  }

  @Test
  public void getAllMethod_should_invoke_list__of_activityAccessPermissionService() {
    facade.getAll();
    verify(activityAccessPermissionService, Mockito.times(1)).list();
  }

  @Test
  public void updateMethod_should_invoke_update__of_activityAccessPermissionService() {
    facade.update(permission);
    verify(activityAccessPermissionService, Mockito.times(1)).update(permission);
  }

}
