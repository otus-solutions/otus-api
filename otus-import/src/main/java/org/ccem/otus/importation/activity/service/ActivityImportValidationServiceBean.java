package org.ccem.otus.importation.activity.service;

import br.org.otus.persistence.UserDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.importation.activity.ActivityImportResultDTO;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.navigation.enums.NavigationTrackingItemStatuses;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.ActivityConfigurationDao;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.ccem.otus.survey.template.item.questions.Question;
import org.ccem.otus.survey.template.item.questions.fillingRules.validators.Mandatory;
import org.ccem.otus.survey.template.item.questions.fillingRules.validators.generic.GenericValidator;
import org.ccem.otus.survey.template.navigation.route.RouteCondition;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public ActivityImportResultDTO validateActivity(SurveyJumpMap surveyJumpMap,SurveyActivity importActivity) throws DataNotFoundException {
        ActivityImportResultDTO activityImportResultDTO = new ActivityImportResultDTO();
        validateRecruitmentNumber(activityImportResultDTO, importActivity.getParticipantData().getRecruitmentNumber());
        validateInterviewer(activityImportResultDTO, importActivity.getLastStatusByName(String.valueOf(ActivityStatusOptions.CREATED)));
        validateCategory(activityImportResultDTO, importActivity.getCategory().getName());
        if (importActivity.getMode().name().equals(String.valueOf(ActivityMode.PAPER))) {
            validatePaperInterviewer(activityImportResultDTO, importActivity.getLastStatusByName(String.valueOf(ActivityStatusOptions.INITIALIZED_OFFLINE)));
        }

        List<SurveyItem> itemContainer = importActivity.getSurveyForm().getSurveyTemplate().itemContainer;

        int itemContainerSize = itemContainer.size();
        for (int i = 0; i < itemContainerSize; i++){
            SurveyItem surveyItem = itemContainer.get(i);
            String templateID = surveyItem.getTemplateID();
            String questionType = surveyItem.objectType;
            String validOrigin = surveyJumpMap.getValidOrigin(templateID);

            Optional<QuestionFill> questionFill = importActivity.getFillContainer().getQuestionFill(templateID);
            if (validOrigin != null){
                NavigationTrackingItem item = importActivity.getNavigationTracker().items.get(i);
                item.previous = validOrigin;
                if(questionType.equals("TextItem") || questionType.equals("ImageItem")){
                    item.state = String.valueOf(NavigationTrackingItemStatuses.VISITED);
                    importActivity.getNavigationTracker().items.set(i,item);
                    surveyJumpMap.validateDefaultJump(templateID);
                } else {
                    if (questionFill.isPresent()) {
                        item.state = String.valueOf(NavigationTrackingItemStatuses.ANSWERED);
                        importActivity.getNavigationTracker().items.set(i, item);
                        ArrayList<SurveyJumpMap.AlternativeDestination> questionAlternativeRoutes = surveyJumpMap.getQuestionAlternativeRoutes(templateID);
                        boolean validAlternativeRoute = false;
                        for (SurveyJumpMap.AlternativeDestination alternativeDestination : questionAlternativeRoutes) {
                            if (routeIsValid(alternativeDestination, importActivity)) {
                                surveyJumpMap.setValidJump(templateID, alternativeDestination.getDestination());
                                validAlternativeRoute = true;
                                break;
                            }
                        }
                        if(!validAlternativeRoute){
                            surveyJumpMap.validateDefaultJump(templateID);
                        }
                    } else {
                        Map<String, GenericValidator> validators = ((Question) surveyItem).fillingRules.options.getValidators();
                        Mandatory mandatory = (Mandatory) (validators.get("mandatory"));
                        if(mandatory != null && !mandatory.data.reference) {
                            item.state = String.valueOf(NavigationTrackingItemStatuses.IGNORED);
                            importActivity.getNavigationTracker().items.set(i, item);
                        } else {
                            activityImportResultDTO.setFailImport();
                            activityImportResultDTO.setQuestionFillError(templateID,true);
                            break;
                        }
                    }
                }
            } else {
                NavigationTrackingItem item = importActivity.getNavigationTracker().items.get(i);
                if(questionFill.isPresent()){
                    activityImportResultDTO.setFailImport();
                    activityImportResultDTO.setQuestionFillError(templateID,false);
                    break;
                } else {
                    item.state = String.valueOf(NavigationTrackingItemStatuses.SKIPPED);
                    importActivity.getNavigationTracker().items.set(i,item);
                }
            }
        }
        activityImportResultDTO.setSurveyActivity(importActivity);
        return activityImportResultDTO;
    }

    private boolean routeIsValid(SurveyJumpMap.AlternativeDestination alternativeDestination, SurveyActivity importActivity) throws DataNotFoundException {
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
                } else {
                    rulesHaveBeenMet = false;
                    break;
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
