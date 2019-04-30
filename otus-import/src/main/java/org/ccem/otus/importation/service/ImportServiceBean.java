package org.ccem.otus.importation.service;

import br.org.otus.persistence.UserDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.importation.activity.ActivityImportDTO;
import org.ccem.otus.importation.activity.ActivityImportResultDTO;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.ActivityConfigurationDao;
import org.ccem.otus.persistence.SurveyJumpMapDao;
import org.ccem.otus.survey.template.item.SurveyItem;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImportServiceBean implements ImportService {

    @Inject
    private SurveyJumpMapDao surveyJumpMapDao;
    @Inject
    private ParticipantDao participantDao;
    @Inject
    private UserDao userDao;
    @Inject
    private ActivityConfigurationDao activityConfigurationDao;

    @Override
    public List<ActivityImportResultDTO> importActivities(String acronym, Integer version, ActivityImportDTO activityImportDTO) throws DataNotFoundException {
        List<ActivityImportResultDTO> failImports = new ArrayList<>();
        SurveyJumpMap surveyJumpMap = surveyJumpMapDao.get(acronym,version);
        List<SurveyActivity> activityList = activityImportDTO.getActivityList();
        for (SurveyActivity importActivity: activityList){
            ActivityImportResultDTO activityImportResultDTO = new ActivityImportResultDTO();

            validateRecruitmentNumber(activityImportResultDTO, importActivity.getParticipantData().getRecruitmentNumber());
            validateInterviewer(activityImportResultDTO, importActivity.getLastStatusByName("CREATED"));
            validateCategory(activityImportResultDTO, importActivity.getCategory().getName());
            if (importActivity.getMode().name().equals("PAPER")) {
                validatePaperInterviewer(activityImportResultDTO, importActivity.getLastStatusByName("INITIALIZED_OFFLINE"));
            }

            if(activityImportResultDTO.getFailImport()){
                failImports.add(activityImportResultDTO);
            }

            List<SurveyItem> itemContainer = importActivity.getSurveyForm().getSurveyTemplate().itemContainer;
            for (int i=0; i < itemContainer.size(); i++){
                String templateID = itemContainer.get(i).getTemplateID();
                String validOrigin = surveyJumpMap.getValidOrigin(templateID);
                Optional<QuestionFill> questionFill = importActivity.getFillContainer().getQuestionFill(templateID);
                if (validOrigin != null){
                    NavigationTrackingItem item = importActivity.getNavigationTracker().items.get(i);
                    if(questionFill.isPresent()){
                        AnswerFill answer = questionFill.get().getAnswer();
                        
                    } else {
                        item.state = "IGNORED";
                        importActivity.getNavigationTracker().items.set(i,item);
                    }
                } else {
                    NavigationTrackingItem item = importActivity.getNavigationTracker().items.get(i);
                    if(questionFill.isPresent()){
                        activityImportResultDTO.setQuestionFillError(templateID,false);
                    } else {
                        item.state = "SKIPED";
                        importActivity.getNavigationTracker().items.set(i,item);
                    }
                }
            }
        }

        return failImports;
    }

    private ActivityImportResultDTO validateCategory(ActivityImportResultDTO activityImportResultDTO, String categoryName) {
        if(!activityConfigurationDao.categoryExists(categoryName)){
            activityImportResultDTO.setCategoryValidationResult(categoryName,false);
            activityImportResultDTO.setFailImport();
        }
        return activityImportResultDTO;
    }

    private ActivityImportResultDTO validatePaperInterviewer(ActivityImportResultDTO activityImportResultDTO, Optional<ActivityStatus> initializedOffline) {
        if (initializedOffline.isPresent()) {
            if (!userDao.exists(initializedOffline.get().getUser().getEmail())) {
                activityImportResultDTO.setPaperInterviewerValidationResult(initializedOffline.get().getUser().getEmail(),false);
                activityImportResultDTO.setFailImport();
            }
        } else {
            activityImportResultDTO.setPaperInterviewerValidationResult("NOT_IDENTIFIED",false);
            activityImportResultDTO.setFailImport();
        }
        return activityImportResultDTO;
    }

    private ActivityImportResultDTO validateInterviewer(ActivityImportResultDTO activityImportResultDTO, Optional<ActivityStatus> created) {
        if(created.isPresent()) {
            if (!userDao.exists(created.get().getUser().getEmail())) {
                activityImportResultDTO.setInterviewerValidationResult(created.get().getUser().getEmail(),false);
                activityImportResultDTO.setFailImport();
            }
        } else {
            activityImportResultDTO.setInterviewerValidationResult("NOT_IDENTIFIED",false);
            activityImportResultDTO.setFailImport();
        }
        return activityImportResultDTO;
    }

    private ActivityImportResultDTO validateRecruitmentNumber(ActivityImportResultDTO activityImportResultDTO, Long recruitmentNumber){
        if (!participantDao.exists(recruitmentNumber)){
            activityImportResultDTO.setRecruitmentNumberValidationResult(recruitmentNumber,false);
            activityImportResultDTO.setFailImport();
        }
        return activityImportResultDTO;
    }

}
