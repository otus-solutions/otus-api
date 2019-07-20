package br.org.otus.survey.activity.api;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.variables.StaticVariableRequestDTO;
import org.ccem.otus.service.ActivityService;

import com.google.gson.JsonSyntaxException;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class ActivityFacade {

	@Inject
	private ActivityService activityService;

	public List<SurveyActivity> list(long rn, String userEmail) {
		return activityService.list(rn, userEmail);
	}

	public SurveyActivity getByID(String id) {
		try {
			return activityService.getByID(id);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public List<SurveyActivity> get(String acronym, Integer version){
		try {
			return activityService.get(acronym, version);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		} catch (MemoryExcededException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public String create(SurveyActivity surveyActivity) {
		return activityService.create(surveyActivity);
	}

	public SurveyActivity updateActivity(SurveyActivity surveyActivity) {
		try {
			return activityService.update(surveyActivity);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public boolean updateCheckerActivity(String checkerUpdated) {
		try {
			return activityService.updateCheckerActivity(checkerUpdated);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(
					ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
		}
	}

	public StaticVariableRequestDTO listVariables(String listVariables) {
        try {
            //TODO - line 76 and 77 are method for use gateway, descommit two line
//            GatewayResponse variables = new GatewayFacade().findCurrentVariables(listVariables);
//            return VariablesDTO.deserialize(variables.getData().toString());
            return StaticVariableRequestDTO.deserialize(listVariables);
        } catch (JsonSyntaxException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }//TODO - line 82 and 83 case for exception, descommit three line
//        catch (IOException e) {
//            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
//        }
    }
	@SuppressWarnings("static-access")
	public SurveyActivity deserialize(String surveyActivity) {
		try {
			return SurveyActivity.deserialize(surveyActivity);
		} catch (JsonSyntaxException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

}
