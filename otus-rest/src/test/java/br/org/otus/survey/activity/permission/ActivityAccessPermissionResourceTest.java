package br.org.otus.survey.activity.permission;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

import java.util.Arrays;
import java.util.List;

import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.GsonBuilder;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ActivityAccessPermission.class})
public class ActivityAccessPermissionResourceTest {
  private static final String EXPECTED_RESPONSE = "{\"data\":true}";
  @InjectMocks
  ActivityAccessPermissionResource resource;
  @Mock
  private ActivityAccessPermissionFacade activityAccessPermissionFacade;
  @Mock
  private ActivityAccessPermission activityAccessPermission;
  private List<String> exclusiveDisjunction;
  private String permissionJson;
  private String permission;
  private ActivityAccessPermission permissionObject;
  private GsonBuilder gsonBuilder = new GsonBuilder();

  @Before
  public void setUp() throws Exception {
    setInternalState(activityAccessPermission, "objectType", "ActivityAccessPermission");
    setInternalState(activityAccessPermission, "version", 1);
    setInternalState(activityAccessPermission, "acronym", "FRC");
    setInternalState(activityAccessPermission, "objectType", "ActivityAccessPermission");
    exclusiveDisjunction = Arrays.asList("vianna.emanoel@gmail.com", "pedro.silva@gmail.com");
    setInternalState(activityAccessPermission, "exclusiveDisjunction", exclusiveDisjunction);

    mockStatic(ActivityAccessPermission.class);
    permissionJson = ActivityAccessPermission.serialize(activityAccessPermission);    
  }

  @Test
  public void createMethod_should_invoke_create_of_activityAccessPermissionFacade() {
    assertEquals(EXPECTED_RESPONSE, resource.create(permission));
    verify(activityAccessPermissionFacade, Mockito.times(1)).create(permissionObject);
  }

  @Test
  public void getAllMethod_should_invoke_getAll_of_activityAccessPermissionFacade() {
    when(ActivityAccessPermission.getGsonBuilder()).thenReturn(gsonBuilder);
    resource.getAll();
    verify(activityAccessPermissionFacade, Mockito.times(1)).getAll();
  }

  @Test
  public void updateMethod_should_invoke_update_activityAccessPermissionFacade() {
    assertEquals(EXPECTED_RESPONSE, resource.update(permissionJson));
    verify(activityAccessPermissionFacade, Mockito.times(1)).update(permissionObject);
  }
}
