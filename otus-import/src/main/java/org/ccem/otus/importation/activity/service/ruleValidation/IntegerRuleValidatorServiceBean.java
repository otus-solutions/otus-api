package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.IntegerAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;

public class IntegerRuleValidatorServiceBean implements IntegerRuleValidatorService {

    @Override
    public boolean run(Rule rule, AnswerFill answer) {
        Long integerRuleAnswer = Long.parseLong(rule.answer);
        IntegerAnswer integerAnswer = (IntegerAnswer) answer;
        switch (rule.operator){
            case "equal":
                if(!isEqual(integerRuleAnswer, integerAnswer.getValue())){
                    return false;
                }
                break;
            case "notEqual":
                if(isEqual(integerRuleAnswer, integerAnswer.getValue())){
                    return false;
                }
                break;
            case "greater":
                if(!isGreater(integerRuleAnswer, integerAnswer.getValue())){
                    return false;
                }
                break;
            case "greaterEqual":
                if(!isGreaterEqual(integerRuleAnswer, integerAnswer.getValue())){
                    return false;
                }
                break;
            case "lower":
                if(!isLower(integerRuleAnswer, integerAnswer.getValue())){
                    return false;
                }
                break;
            case "lowerEqual":
                if(!isLowerEqual(integerRuleAnswer, integerAnswer.getValue())){
                    return false;
                }
                break;
        }
        return true;
    }

    private boolean isEqual(Long ruleAnswer, Long answer) {
        return ruleAnswer.equals(answer);
    }

    private boolean isGreater(Long ruleAnswer, Long answer) {
        return answer > ruleAnswer;
    }

    private boolean isGreaterEqual(Long ruleAnswer, Long answer) {
        return (isGreater(ruleAnswer,answer) || isEqual(ruleAnswer,answer));
    }

    private boolean isLower(Long ruleAnswer, Long answer) {
        return answer < ruleAnswer;
    }

    private boolean isLowerEqual(Long ruleAnswer, Long answer) {
        return (isLower(ruleAnswer,answer) || isEqual(ruleAnswer,answer));
    }

}
