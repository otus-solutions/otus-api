package br.org.otus.survey.activity.permission;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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

  private static final String ACRONYM = "ABC";
  private static final Integer VERSION = 1;

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
  public void updateMethod_should_invoke_update__of_activityAccessPermissionService() {
    facade.update(permission);
    verify(activityAccessPermissionService, Mockito.times(1)).update(permission);
  }

  @Test
  public void getAllMethod_should_invoke_list__of_activityAccessPermissionService() {
    facade.getAll();
    verify(activityAccessPermissionService, Mockito.times(1)).list();
  }

  @Test
  public void getMethod_should_invoke_update_of_activityAccessPermissionService() throws DataNotFoundException {
    facade.get(ACRONYM, VERSION.toString());
    verify(activityAccessPermissionService, Mockito.times(1)).get(ACRONYM, VERSION);
  }

  @Test(expected = HttpResponseException.class)
  public void getMethod_should_handle_DataNotFoundException() throws Exception {
    when(activityAccessPermissionService, "get", ACRONYM, VERSION).thenThrow(new DataNotFoundException());
    facade.get(ACRONYM, VERSION.toString());
  }

}
