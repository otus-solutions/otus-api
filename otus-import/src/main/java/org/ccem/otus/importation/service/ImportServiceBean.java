package org.ccem.otus.importation.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.importation.activity.ActivityImportDTO;
import org.ccem.otus.importation.activity.ActivityImportResultDTO;
import org.ccem.otus.importation.activity.service.ActivityImportValidationService;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;
import org.ccem.otus.persistence.ActivityDao;
import org.ccem.otus.persistence.SurveyJumpMapDao;
import org.ccem.otus.service.ActivityServiceBean;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ImportServiceBean implements ImportService {

    @Inject
    private SurveyJumpMapDao surveyJumpMapDao;
    @Inject
    private ActivityServiceBean activityServiceBean;
    @Inject
    private ActivityImportValidationService activityImportValidationService;

    @Override
    public List<ActivityImportResultDTO> importActivities(String acronym, Integer version, ActivityImportDTO activityImportDTO) throws DataNotFoundException {
        List<ActivityImportResultDTO> failImports = new ArrayList<>();
        SurveyJumpMap surveyJumpMap = surveyJumpMapDao.get(acronym,version);
        List<SurveyActivity> activityList = activityImportDTO.getActivityList();

        for (SurveyActivity importActivity: activityList){
            ActivityImportResultDTO activityImportResultDTO = activityImportValidationService.validateActivity(surveyJumpMap, importActivity);
            if(activityImportResultDTO.getFailImport()){
                failImports.add(activityImportResultDTO);
            } else {
                activityServiceBean.create(importActivity);
            }
        }

        return failImports;
    }

}
