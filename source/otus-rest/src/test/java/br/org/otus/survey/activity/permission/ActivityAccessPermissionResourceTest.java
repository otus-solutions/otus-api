package br.org.otus.survey.activity.permission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
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

import br.org.otus.permission.ActivityPermissionResource;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivityAccessPermission.class})
public class ActivityAccessPermissionResourceTest {

  private static final ObjectId OBJECT_ID = new ObjectId();
  private static final String OBJECT_TYPE = "ActivityPermission";
  private static final String ACRONYM = "ABC";
  private static final Integer VERSION = 1;
  private static final String EXPECTED_NOT_GET_RESPONSE = "{\"data\":true}";

  @InjectMocks
  private ActivityPermissionResource resource;
  @Mock
  private ActivityAccessPermissionFacade activityAccessPermissionFacade;

  private ActivityAccessPermission activityAccessPermission = new ActivityAccessPermission();
  private List<String> exclusiveDisjunction;
  private String activityAccessPermissionJson;
  private ActivityAccessPermission permissionObject;
  private GsonBuilder gsonBuilder = new GsonBuilder();

  @Before
  public void setUp() {
    setInternalState(activityAccessPermission, "_id", OBJECT_ID);
    setInternalState(activityAccessPermission, "objectType", OBJECT_TYPE);
    setInternalState(activityAccessPermission, "version", VERSION);
    setInternalState(activityAccessPermission, "acronym", ACRONYM);
    exclusiveDisjunction = Arrays.asList("user1@gmail.com", "user2@gmail.com");
    setInternalState(activityAccessPermission, "exclusiveDisjunction", exclusiveDisjunction);

    mockStatic(ActivityAccessPermission.class);
    activityAccessPermissionJson = ActivityAccessPermission.serialize(activityAccessPermission);
  }

  @Test
  public void createMethod_should_invoke_create_of_activityAccessPermissionFacade() {
    assertEquals(EXPECTED_NOT_GET_RESPONSE, resource.create(activityAccessPermissionJson));
    verify(activityAccessPermissionFacade, Mockito.times(1)).create(permissionObject);
  }

  @Test
  public void updateMethod_should_invoke_update_activityAccessPermissionFacade() {
    assertEquals(EXPECTED_NOT_GET_RESPONSE, resource.update(activityAccessPermissionJson));
    verify(activityAccessPermissionFacade, Mockito.times(1)).update(permissionObject);
  }

  @Test
  public void getAllMethod_should_invoke_getAll_of_activityAccessPermissionFacade() {
    when(ActivityAccessPermission.getGsonBuilder()).thenReturn(gsonBuilder);
    resource.getAll();
    verify(activityAccessPermissionFacade, Mockito.times(1)).getAll();
  }

  @Test
  public void getMethod_should_invoke_getAll_of_activityAccessPermissionFacade() {
    when(ActivityAccessPermission.getGsonBuilder()).thenReturn(gsonBuilder);
    resource.get(ACRONYM, VERSION.toString());
    verify(activityAccessPermissionFacade, Mockito.times(1)).get(ACRONYM, VERSION.toString());
  }
}
