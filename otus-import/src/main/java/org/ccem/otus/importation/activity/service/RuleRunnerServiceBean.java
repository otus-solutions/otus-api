package org.ccem.otus.importation.activity.service;

import org.ccem.otus.importation.activity.service.ruleValidation.*;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.survey.template.navigation.route.Rule;

import javax.inject.Inject;
import java.util.Optional;

public class RuleRunnerServiceBean implements RuleRunnerService {

    @Inject
    private NumericRuleValidatorService numericRuleValidatorService;
    @Inject
    private TextRuleValidatorService textRuleValidatorService;
    @Inject
    private TimeRuleValidatorService timeRuleValidatorService;
    @Inject
    private CheckboxRuleValidatorService checkboxRuleValidatorService;
    @Inject
    private CalendarRuleValidatorService calendarRuleValidatorService;

    @Override
    public boolean run(Rule rule, Optional<QuestionFill> ruleQuestionFill) {
        AnswerFill answer = ruleQuestionFill.get().getAnswer();
        switch (answer.getType()){
            case "IntegerQuestion" :
            case "DecimalQuestion" :
            case "SingleSelectionQuestion" :
            case "FileUploadQuestion" :
            case "GridTextQuestion" :
            case "GridIntegerQuestion" :
                if(!numericRuleValidatorService.run(rule,answer)){
                    return false;
                }
                break;
            case "TextQuestion":
            case "EmailQuestion":
            case "AutocompleteQuestion":
                if(!textRuleValidatorService.run(rule,answer)){
                    return false;
                }
                break;
            case "TimeQuestion":
                if(!timeRuleValidatorService.run(rule,answer)){
                    return false;
                }
                break;
            case "CheckboxQuestion":
                if(!checkboxRuleValidatorService.run(rule,answer)){
                    return false;
                }
                break;
            case "CalendarQuestion":
                if(!calendarRuleValidatorService.run(rule,answer)){
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }
}
