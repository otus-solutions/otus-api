package org.ccem.otus.service.permission;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityAccessPermissionDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ActivityAccessPermissionServiceBeanTest {

  private static final String ACRONYM = "ABC";
  private static final Integer VERSION = 1;

  @InjectMocks
  private ActivityAccessPermissionServiceBean service;
  @Mock
  private ActivityAccessPermissionDao activityAccessPermissionDao;
  @Mock
  private ActivityAccessPermission activityAccessPermission;

  @Test
  public void listMethod_should_invoke_find_of_activityAccessPermissionDao() {
    service.list();
    verify(activityAccessPermissionDao, times(1)).find();
  }

  @Test
  public void createMethod_should_invoke_persist_of_activityAccessPermissionDao() {
    service.create(activityAccessPermission);
    verify(activityAccessPermissionDao, times(1)).persist(activityAccessPermission);
  }

  @Test
  public void updateMethod_should_invoke_() {
    service.update(activityAccessPermission);
    verify(activityAccessPermissionDao, times(1)).update(activityAccessPermission);
  }

  @Test
  public void get_method_should_invoke_get_of_activityAccessPermissionDao() throws DataNotFoundException {
    service.get(ACRONYM, VERSION);
    verify(activityAccessPermissionDao, times(1)).get(ACRONYM, VERSION);
  }
}
