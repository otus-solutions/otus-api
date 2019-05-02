package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.template.navigation.route.Rule;

public class TextRuleValidatorServiceBean implements TextRuleValidatorService {
    @Override
    public boolean run(Rule rule, AnswerFill answer) {
        return false;
    }
}
