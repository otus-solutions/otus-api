package br.org.otus.survey.activity.permission;

import java.util.List;

import javax.inject.Inject;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;

public class ActivityAccessPermissionFacade {

  @Inject
  private ActivityAccessPermissionService activityAccessPermissionService;

  public void create(ActivityAccessPermission permission) {
    activityAccessPermissionService.create(permission);
  }

  public void update(ActivityAccessPermission permission) {
    activityAccessPermissionService.update(permission);
  }

  public List<ActivityAccessPermission> getAll() {
    return activityAccessPermissionService.list();
  }

  public ActivityAccessPermission get(String acronym, String version) {
    try {
      return activityAccessPermissionService.get(acronym, Integer.parseInt(version));
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }
}
