package br.org.otus.user.api.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.service.pendency.UserActivityPendencyService;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.inject.Inject;
import java.util.List;

public class UserActivityPendencyFacade {

  @Inject
  private UserActivityPendencyService userActivityPendencyService;

  public void create(String userActivityPendencyJson) {
    try {
      UserActivityPendency userActivityPendency = UserActivityPendency.deserialize(userActivityPendencyJson);
      userActivityPendencyService.create(userActivityPendency); //inserted UserActivityPendency
    } catch (ValidationException | DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void update(String userActivityPendencyJson) {
    try {
      UserActivityPendency userActivityPendency = UserActivityPendency.deserialize(userActivityPendencyJson);
      userActivityPendencyService.update(userActivityPendency); //inserted UserActivityPendency
    } catch (ValidationException | DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public List<UserActivityPendency> list() {
    try {
      return userActivityPendencyService.list();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public UserActivityPendency getByActivityId(String activityId) {
    try {
      return userActivityPendencyService.getByActivityId(Long.parseLong(activityId));
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void delete(String userActivityPendencyJson) {
    try {
      UserActivityPendency userActivityPendency = UserActivityPendency.deserialize(userActivityPendencyJson);
      userActivityPendencyService.delete(userActivityPendency);
    } catch (ValidationException | DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

}
