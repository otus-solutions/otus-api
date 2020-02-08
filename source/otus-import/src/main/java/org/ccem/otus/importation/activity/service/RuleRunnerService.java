package org.ccem.otus.importation.activity.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.survey.template.navigation.route.Rule;

import java.util.Optional;

public interface RuleRunnerService {

  boolean run(Rule rule, Optional<QuestionFill> ruleQuestionFill) throws DataNotFoundException;
}
