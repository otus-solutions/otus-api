package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.ImmutableDateAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.time.LocalDateTime;

public class TimeRuleValidatorServiceBean implements TimeRuleValidatorService {
    @Override
    public boolean run(Rule rule, AnswerFill answer) {
        ImmutableDate immutableDateRuleAnswer = new ImmutableDate(rule.answer);
        ImmutableDateAnswer immutableDateAnswer = (ImmutableDateAnswer) answer;
        switch (rule.operator){
            case "equal":
                if(!isEqual(immutableDateRuleAnswer.getValue(), immutableDateAnswer.getValue().getValue())){
                    return false;
                }
                break;
            case "notEqual":
                if(isEqual(immutableDateRuleAnswer.getValue(), immutableDateAnswer.getValue().getValue())){
                    return false;
                }
                break;
            case "greater":
                if(!isGreater(immutableDateRuleAnswer.getValue(), immutableDateAnswer.getValue().getValue())){
                    return false;
                }
                break;
            case "greaterEqual":
                if(!isGreaterEqual(immutableDateRuleAnswer.getValue(), immutableDateAnswer.getValue().getValue())){
                    return false;
                }
                break;
            case "lower":
                if(!isLower(immutableDateRuleAnswer.getValue(), immutableDateAnswer.getValue().getValue())){
                    return false;
                }
                break;
            case "lowerEqual":
                if(!isLowerEqual(immutableDateRuleAnswer.getValue(), immutableDateAnswer.getValue().getValue())){
                    return false;
                }
                break;
        }
        return true;
    }

    private boolean isEqual(LocalDateTime immutableDateRuleAnswer, LocalDateTime answer) {
        return immutableDateRuleAnswer.equals(answer);
    }

    private boolean isGreater(LocalDateTime immutableDateRuleAnswer, LocalDateTime answer) {
        return answer.isAfter(immutableDateRuleAnswer);
    }

    private boolean isGreaterEqual(LocalDateTime immutableDateRuleAnswer, LocalDateTime answer) {
        return (isGreater(immutableDateRuleAnswer,answer) || isEqual(immutableDateRuleAnswer,answer));
    }

    private boolean isLower(LocalDateTime immutableDateRuleAnswer, LocalDateTime answer) {
        return answer.isBefore(immutableDateRuleAnswer);
    }

    private boolean isLowerEqual(LocalDateTime immutableDateRuleAnswer, LocalDateTime answer) {
        return (isLower(immutableDateRuleAnswer,answer) || isEqual(immutableDateRuleAnswer,answer));
    }

}
