package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;

public interface SurveyJumpMapDao {

    void persist(SurveyJumpMap surveyJumpMap);

    SurveyJumpMap get(String acronym, Integer version) throws DataNotFoundException;
}
