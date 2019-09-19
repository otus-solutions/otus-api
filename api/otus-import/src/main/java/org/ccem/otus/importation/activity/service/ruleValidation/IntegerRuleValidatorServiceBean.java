package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.IntegerAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.TextAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;

public class IntegerRuleValidatorServiceBean implements IntegerRuleValidatorService {

    @Override
    public boolean run(Rule rule, AnswerFill answer) throws DataNotFoundException {
        Long integerRuleAnswer = Long.parseLong(rule.answer);
        Long longAnswer;
        if (answer.getType().equals("IntegerQuestion")){
            longAnswer = ((IntegerAnswer) answer).getValue();
        } else {
            try {
                longAnswer = Long.parseLong(((TextAnswer) answer).getValue());
            } catch (NumberFormatException e) {
                return false;
            }
        }

        switch (rule.operator){
            case "equal":
                if(!isEqual(integerRuleAnswer, longAnswer)){
                    return false;
                }
                break;
            case "notEqual":
                if(isEqual(integerRuleAnswer, longAnswer)){
                    return false;
                }
                break;
            case "greater":
                if(!isGreater(integerRuleAnswer, longAnswer)){
                    return false;
                }
                break;
            case "greaterEqual":
                if(!isGreaterEqual(integerRuleAnswer, longAnswer)){
                    return false;
                }
                break;
            case "lower":
                if(!isLower(integerRuleAnswer, longAnswer)){
                    return false;
                }
                break;
            case "lowerEqual":
                if(!isLowerEqual(integerRuleAnswer, longAnswer)){
                    return false;
                }
                break;
            default:
                throw new DataNotFoundException(new Throwable("Rule operator {" + rule.operator + "} for "+ answer.getType() +" not found."));
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
