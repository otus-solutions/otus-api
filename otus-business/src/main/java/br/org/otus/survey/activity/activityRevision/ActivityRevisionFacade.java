package br.org.otus.survey.activity.activityRevision;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import com.google.gson.JsonSyntaxException;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.service.activityRevision.ActivityRevisionService;

import javax.inject.Inject;
import java.util.List;

public class ActivityRevisionFacade {

	@Inject
	private ActivityRevisionService activityRevisionService;

	public String create(String activityRevision) {
		try {
			return activityRevisionService.create(ActivityRevision.deserialize(activityRevision));
		} catch (JsonSyntaxException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public List<ActivityRevision> list(String activityId) {
		ObjectId activityOid = new ObjectId(activityId);
		return activityRevisionService.list(activityOid);
	}

}
