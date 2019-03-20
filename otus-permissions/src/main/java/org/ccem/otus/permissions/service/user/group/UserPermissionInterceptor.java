package org.ccem.otus.permissions.service.user.group;

import org.ccem.otus.permissions.service.user.UserPermissionService;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.List;

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
