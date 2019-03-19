package org.ccem.otus.permissions.service.user;

import org.ccem.otus.permissions.persistence.user.UserPermissionGenericDao;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.List;

@Interceptor
@UserPermission
public class UserPermissionInterceptor {

  @Inject
  private UserPermissionGenericDao userPermissionGenericDao;

  @AroundInvoke
  public Object logMethodEntry(InvocationContext ctx) throws Exception {
    Object[] parameters = ctx.getParameters();
    List<String> permittedActivities = userPermissionGenericDao.getUserPermittedActivities((String) parameters[1]);
    parameters[0] = permittedActivities;
    ctx.setParameters(parameters);
    return ctx.proceed();
  }



}
