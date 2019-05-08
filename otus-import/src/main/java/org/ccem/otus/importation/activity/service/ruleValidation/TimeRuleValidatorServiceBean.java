package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.ImmutableDateAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeRuleValidatorServiceBean implements TimeRuleValidatorService {
    @Override
    public boolean run(String type, Rule rule, AnswerFill answer) throws DataNotFoundException {
        ImmutableDate immutableDateRuleAnswer;
        ImmutableDateAnswer immutableDateAnswer;

        try {
            if (type.equals("CalendarQuestion")){
                String[] split = rule.answer.split("/");
                immutableDateRuleAnswer = new ImmutableDate(LocalDate.of(Integer.parseInt(split[2]),Integer.parseInt(split[1]),Integer.parseInt(split[0])));
                immutableDateAnswer = (ImmutableDateAnswer) answer;
            } else {
                String[] split = rule.answer.split(":");
                immutableDateRuleAnswer = new ImmutableDate("0001-01-01 ".concat(split[0]).concat(":").concat(split[1]).concat(":00.000"));
                immutableDateAnswer = (ImmutableDateAnswer) answer;
            }
        } catch (Exception e) {
            return false;
        }

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
            default:
                throw new DataNotFoundException(new Throwable("Rule operator {" + rule.operator + "} for "+ answer.getType() +" not found."));
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
