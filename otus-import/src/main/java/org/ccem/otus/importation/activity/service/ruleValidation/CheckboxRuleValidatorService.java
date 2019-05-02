package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.template.navigation.route.Rule;

public interface CheckboxRuleValidatorService {
    boolean run(Rule rule, AnswerFill answer);
}
