package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.TextAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;

public class TextRuleValidatorServiceBean implements TextRuleValidatorService {
    @Override
    public boolean run(Rule rule, AnswerFill answer) {
        String textRuleAnswer = rule.answer;
        TextAnswer textAnswer = (TextAnswer) answer;
        switch (rule.operator){
            case "equal":
                if(!isEqual(textRuleAnswer, textAnswer.getValue())){
                    return false;
                }
                break;
            case "notEqual":
                if(isEqual(textRuleAnswer, textAnswer.getValue())){
                    return false;
                }
                break;
            case "greater":
                if(!contains(textRuleAnswer, textAnswer.getValue())){
                    return false;
                }
                break;
        }
        return false;
    }

    private boolean contains(String textRuleAnswer, String answer) {
        return answer.contains(textRuleAnswer);
    }

    private boolean isEqual(String ruleAnswer, String answer) {
        return ruleAnswer.equals(answer);
    }
}
