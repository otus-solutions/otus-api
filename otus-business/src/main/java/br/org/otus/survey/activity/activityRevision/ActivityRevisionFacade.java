package br.org.otus.survey.activity.activityRevision;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.user.api.UserFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.user.ActivityBasicUser;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.model.survey.activity.user.BasicUserFactory;
import org.ccem.otus.service.activityRevision.ActivityRevisionService;

import javax.inject.Inject;
import java.util.List;

public class ActivityRevisionFacade {

    @Inject
    private ActivityRevisionService activityRevisionService;

    @Inject
    private UserFacade userFacade;

    public void create(String activityRevisionJson, String userEmail) {

            BasicUserFactory basicUserFactory = new BasicUserFactory();
            ActivityBasicUser user = basicUserFactory.createRevisionUser(userFacade.fetchByEmail(userEmail));

            activityRevisionService.create(activityRevisionJson, user);
    }

    public List<ActivityRevision> list(String activityId) {
        try {
        return activityRevisionService.list(activityId);
        } catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
    }

}
