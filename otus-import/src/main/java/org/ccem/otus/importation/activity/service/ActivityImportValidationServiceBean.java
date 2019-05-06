package org.ccem.otus.importation.activity.service;

import br.org.otus.persistence.UserDao;
import org.ccem.otus.importation.activity.ActivityImportResultDTO;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.ActivityConfigurationDao;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.ccem.otus.survey.template.navigation.route.RouteCondition;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActivityImportValidationServiceBean implements ActivityImportValidationService {

    @Inject
    private ParticipantDao participantDao;
    @Inject
    private UserDao userDao;
    @Inject
    private ActivityConfigurationDao activityConfigurationDao;
    @Inject
    private RuleRunnerService ruleRunnerService;

    @Override
    public ActivityImportResultDTO validateActivity(SurveyJumpMap surveyJumpMap,SurveyActivity importActivity) {
        ActivityImportResultDTO activityImportResultDTO = new ActivityImportResultDTO();
        validateRecruitmentNumber(activityImportResultDTO, importActivity.getParticipantData().getRecruitmentNumber());
        validateInterviewer(activityImportResultDTO, importActivity.getLastStatusByName("CREATED"));
        validateCategory(activityImportResultDTO, importActivity.getCategory().getName());
        if (importActivity.getMode().name().equals("PAPER")) {
            validatePaperInterviewer(activityImportResultDTO, importActivity.getLastStatusByName("INITIALIZED_OFFLINE"));
        }

        List<SurveyItem> itemContainer = importActivity.getSurveyForm().getSurveyTemplate().itemContainer;

        for (int i=0; i < itemContainer.size(); i++){

            String templateID = itemContainer.get(i).getTemplateID();
            String validOrigin = surveyJumpMap.getValidOrigin(templateID);

            Optional<QuestionFill> questionFill = importActivity.getFillContainer().getQuestionFill(templateID);
            if (validOrigin != null){
                NavigationTrackingItem item = importActivity.getNavigationTracker().items.get(i);
                item.previous = validOrigin;
                if(questionFill.isPresent()){
                    item.state = "ANSWERED";
                    importActivity.getNavigationTracker().items.set(i,item);
                    ArrayList<SurveyJumpMap.AlternativeDestination> questionAlternativeRoutes = surveyJumpMap.getQuestionAlternativeRoutes(templateID);
                    for (SurveyJumpMap.AlternativeDestination alternativeDestination :questionAlternativeRoutes){
                        if(routeIsValid(alternativeDestination,importActivity)){
                            surveyJumpMap.setValidJump(templateID,alternativeDestination.getDestination());
                            break;
                        }
                    }
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
        activityImportResultDTO.setSurveyActivity(importActivity);
        return activityImportResultDTO;
    }

    private boolean routeIsValid(SurveyJumpMap.AlternativeDestination alternativeDestination, SurveyActivity importActivity){
        boolean routeIsValid = false;
        for(RouteCondition routeCondition :alternativeDestination.getRouteConditions()){
            boolean rulesHaveBeenMet = true;
            for(Rule rule : routeCondition.rules){
                Optional<QuestionFill> ruleQuestionFill = importActivity.getFillContainer().getQuestionFill(rule.when);
                if (ruleQuestionFill.isPresent()){
                    boolean ruleWasMet = ruleRunnerService.run(rule,ruleQuestionFill);
                    if(!ruleWasMet){
                        rulesHaveBeenMet = false;
                        break;
                    }
                }
            }
            if (rulesHaveBeenMet){
                routeIsValid = true;
                break;
            }
        }
        return routeIsValid;
    }


    private void validateCategory(ActivityImportResultDTO activityImportResultDTO, String categoryName) {
        if(!activityConfigurationDao.categoryExists(categoryName)){
            activityImportResultDTO.setCategoryValidationResult(categoryName,false);
            activityImportResultDTO.setFailImport();
        }
    }

    private void validatePaperInterviewer(ActivityImportResultDTO activityImportResultDTO, @NotNull Optional<ActivityStatus> initializedOffline) {
        if (initializedOffline.isPresent()) {
            if (!userDao.exists(initializedOffline.get().getUser().getEmail())) {
                activityImportResultDTO.setPaperInterviewerValidationResult(initializedOffline.get().getUser().getEmail(),false);
                activityImportResultDTO.setFailImport();
            }
        } else {
            activityImportResultDTO.setPaperInterviewerValidationResult("NOT_IDENTIFIED",false);
            activityImportResultDTO.setFailImport();
        }
    }

    private void validateInterviewer(ActivityImportResultDTO activityImportResultDTO, @NotNull Optional<ActivityStatus> created) {
        if(created.isPresent()) {
            if (!userDao.exists(created.get().getUser().getEmail())) {
                activityImportResultDTO.setInterviewerValidationResult(created.get().getUser().getEmail(),false);
                activityImportResultDTO.setFailImport();
            }
        } else {
            activityImportResultDTO.setInterviewerValidationResult("NOT_IDENTIFIED",false);
            activityImportResultDTO.setFailImport();
        }
    }

    private void validateRecruitmentNumber(ActivityImportResultDTO activityImportResultDTO, Long recruitmentNumber){
        if (!participantDao.exists(recruitmentNumber)){
            activityImportResultDTO.setRecruitmentNumberValidationResult(recruitmentNumber,false);
            activityImportResultDTO.setFailImport();
        }
    }


}
