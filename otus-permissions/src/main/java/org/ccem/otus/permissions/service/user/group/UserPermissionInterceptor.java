package org.ccem.otus.permissions.service.user.group;

import java.util.List;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.ccem.otus.permissions.service.user.UserPermissionService;

@Interceptor
@UserPermission
public class UserPermissionInterceptor {

  @Inject
  private UserPermissionService userPermissionService;

  @AroundInvoke
  public Object logMethodEntry(InvocationContext ctx) throws Exception {
    Object[] parameters = ctx.getParameters();
    List<String> permittedActivities = userPermissionService.getUserPermittedSurveys((String) parameters[1]);
    parameters[0] = permittedActivities;
    ctx.setParameters(parameters);
    return ctx.proceed();
  }

}
