package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.template.navigation.route.Rule;

public interface IntegerRuleValidatorService {
    boolean run(Rule rule, AnswerFill answer) throws DataNotFoundException;
}
