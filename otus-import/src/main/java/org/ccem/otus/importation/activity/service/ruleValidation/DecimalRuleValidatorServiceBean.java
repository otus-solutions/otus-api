package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.DecimalAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;

public class DecimalRuleValidatorServiceBean implements DecimalRuleValidatorService {
    @Override
    public boolean run(Rule rule, AnswerFill answer) throws DataNotFoundException {
        Double decimalRuleAnswer = Double.parseDouble(rule.answer);
        DecimalAnswer decimalAnswer = (DecimalAnswer) answer;
        switch (rule.operator){
            case "equal":
                if(!isEqual(decimalRuleAnswer, decimalAnswer.getValue())){
                    return false;
                }
                break;
            case "notEqual":
                if(isEqual(decimalRuleAnswer, decimalAnswer.getValue())){
                    return false;
                }
                break;
            case "greater":
                if(!isGreater(decimalRuleAnswer, decimalAnswer.getValue())){
                    return false;
                }
                break;
            case "greaterEqual":
                if(!isGreaterEqual(decimalRuleAnswer, decimalAnswer.getValue())){
                    return false;
                }
                break;
            case "lower":
                if(!isLower(decimalRuleAnswer, decimalAnswer.getValue())){
                    return false;
                }
                break;
            case "lowerEqual":
                if(!isLowerEqual(decimalRuleAnswer, decimalAnswer.getValue())){
                    return false;
                }
                break;
            default:
                throw new DataNotFoundException(new Throwable("Rule operator {" + rule.operator + "} for "+ answer.getType() +" not found."));
        }
        return true;
    }

    private boolean isEqual(Double ruleAnswer, Double answer) {
        return ruleAnswer.equals(answer);
    }

    private boolean isGreater(Double ruleAnswer, Double answer) {
        return answer > ruleAnswer;
    }

    private boolean isGreaterEqual(Double ruleAnswer, Double answer) {
        return (isGreater(ruleAnswer,answer) || isEqual(ruleAnswer,answer));
    }

    private boolean isLower(Double ruleAnswer, Double answer) {
        return answer < ruleAnswer;
    }

    private boolean isLowerEqual(Double ruleAnswer, Double answer) {
        return (isLower(ruleAnswer,answer) || isEqual(ruleAnswer,answer));
    }

}
