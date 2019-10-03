package org.ccem.otus.importation.activity.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.importation.activity.ActivityImportResultDTO;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;

public interface ActivityImportValidationService {

    ActivityImportResultDTO validateActivity(SurveyJumpMap surveyJumpMap, SurveyActivity importActivity) throws DataNotFoundException;

}
