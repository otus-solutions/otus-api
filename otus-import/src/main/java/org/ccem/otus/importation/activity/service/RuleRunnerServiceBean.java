package org.ccem.otus.importation.activity.service;

import org.ccem.otus.importation.activity.service.ruleValidation.*;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.survey.template.navigation.route.Rule;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Optional;

public class RuleRunnerServiceBean implements RuleRunnerService {

    @Inject
    private IntegerRuleValidatorService integerRuleValidatorService;
    @Inject
    private DecimalRuleValidatorService decimalRuleValidatorService;
    @Inject
    private TextRuleValidatorService textRuleValidatorService;
    @Inject
    private TimeRuleValidatorService timeRuleValidatorService;
    @Inject
    private CheckboxRuleValidatorService checkboxRuleValidatorService;

    @Override
    public boolean run(Rule rule, Optional<QuestionFill> ruleQuestionFill) {
        AnswerFill answer = ruleQuestionFill.get().getAnswer();
        if(rule.isMetadata && !integerRuleValidatorService.run(rule,answer)){
            return false;
        } else {
            switch (answer.getType()){
                case "IntegerQuestion" :
                case "SingleSelectionQuestion" :
                    if(!integerRuleValidatorService.run(rule,answer)){
                        return false;
                    }
                    break;
                case "DecimalQuestion" :
                    if(!decimalRuleValidatorService.run(rule,answer)){
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
                case "CalendarQuestion":
                    if(!timeRuleValidatorService.run(answer.getType(),rule,answer)){
                        return false;
                    }
                    break;
                case "CheckboxQuestion":
                    if(!checkboxRuleValidatorService.run(rule,answer)){
                        return false;
                    }
                    break;

                default:
                    return false;
            }
        }
        return true;
    }
}
