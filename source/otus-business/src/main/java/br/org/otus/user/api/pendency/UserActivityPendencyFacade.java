package br.org.otus.user.api.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.service.pendency.UserActivityPendencyService;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

import javax.inject.Inject;
import java.util.List;

public class UserActivityPendencyFacade {

  @Inject
  private UserActivityPendencyService userActivityPendencyService;

  public String create(String userEmail, String userActivityPendencyJson) {
    return userActivityPendencyService.create(userEmail, userActivityPendencyJson).toString();
  }

  public void update(String userActivityPendencyId, String userActivityPendencyJson) {
    try {
      userActivityPendencyService.update(userActivityPendencyId, userActivityPendencyJson);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void delete(String userActivityPendencyId) {
    try {
      userActivityPendencyService.delete(userActivityPendencyId);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void deleteByActivityId(String activityId) {
    try {
      String pendencyId = getByActivityId(activityId).getId().toString();
      userActivityPendencyService.delete(pendencyId);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public UserActivityPendency getByActivityId(String activityId) {
    try {
      return userActivityPendencyService.getByActivityId(activityId);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<UserActivityPendency> listAllPendencies(String userEmail) {
    try {
      return userActivityPendencyService.listAllPendencies(userEmail);
    } catch (DataNotFoundException | MemoryExcededException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<UserActivityPendency> listOpenedPendencies(String userEmail) {
    try {
      return userActivityPendencyService.listOpenedPendencies(userEmail);
    } catch (DataNotFoundException | MemoryExcededException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<UserActivityPendency> listDonePendencies(String userEmail) {
    try {
      return userActivityPendencyService.listDonePendencies(userEmail);
    } catch (DataNotFoundException | MemoryExcededException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

}