package org.ccem.otus.permissions.service.user.group;

import org.ccem.otus.permissions.service.user.UserPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.interceptor.InvocationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({InvocationContext.class})
public class UserPermissionInterceptorTest {

  private static final String PERMITTED_ACTIVITIES_EMPTY = "";
  private static final String EMAIL = "otus@tus.com";

  @InjectMocks
  private UserPermissionInterceptor userPermissionInterceptor;
  @Mock
  private UserPermissionService userPermissionService;
  @Mock
  private InvocationContext ctx;

  private Object[] parameters;
  private List<String> permittedActivities;

  @Test
  public void logMethodEntry() throws Exception {
    parameters = new Object[]{PERMITTED_ACTIVITIES_EMPTY, EMAIL};
    assertEquals(parameters[0], PERMITTED_ACTIVITIES_EMPTY);
    when(ctx.getParameters()).thenReturn(parameters);
    permittedActivities = Arrays.asList("AB", "BC", "CD");
    when(userPermissionService.getUserPermittedSurveys((String) parameters[1])).thenReturn(permittedActivities);

    userPermissionInterceptor.logMethodEntry(ctx);
    assertEquals(parameters[0], permittedActivities);
    verify(ctx, times(1)).setParameters(parameters);
    verify(ctx, times(1)).proceed();
  }
}