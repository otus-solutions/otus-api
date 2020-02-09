package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.CheckboxAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.CheckboxAnswerOption;
import org.ccem.otus.survey.template.navigation.route.Rule;

import java.util.List;
import java.util.stream.Collectors;

public class CheckboxRuleValidatorServiceBean implements CheckboxRuleValidatorService {
  @Override
  public boolean run(Rule rule, AnswerFill answer) throws DataNotFoundException {
    String checkboxRuleAnswer = rule.answer;
    CheckboxAnswer checkboxAnswer = (CheckboxAnswer) answer;
    switch (rule.operator) {
      case "equal":
      case "contains":
        if (!isEqual(checkboxRuleAnswer, checkboxAnswer.getValue())) {
          return false;
        }
        break;
      case "notEqual":
        if (isEqual(checkboxRuleAnswer, checkboxAnswer.getValue())) {
          return false;
        }
        break;
      case "quantity":
        if (!quantity(checkboxRuleAnswer, checkboxAnswer.getValue())) {
          return false;
        }
        break;
      case "minSelected":
        if (!minSelected(checkboxRuleAnswer, checkboxAnswer.getValue())) {
          return false;
        }
        break;
      case "maxSelected":
        if (!maxSelected(checkboxRuleAnswer, checkboxAnswer.getValue())) {
          return false;
        }
        break;
      default:
        throw new DataNotFoundException(new Throwable("Rule operator {" + rule.operator + "} for " + answer.getType() + " not found."));
    }
    return true;
  }

  private boolean isEqual(String ruleAnswer, List<CheckboxAnswerOption> answer) {
    List<CheckboxAnswerOption> checkboxAnswerOptions = answer.stream().filter(checkboxAnswerOption -> (checkboxAnswerOption.getOption().equals(ruleAnswer) && checkboxAnswerOption.getState().equals(true))).collect(Collectors.toList());
    return checkboxAnswerOptions.size() > 0;
  }

  private boolean quantity(String ruleAnswer, List<CheckboxAnswerOption> answer) {
    List<CheckboxAnswerOption> checkboxAnswerOptions = answer.stream().filter(checkboxAnswerOption -> checkboxAnswerOption.getState().equals(true)).collect(Collectors.toList());
    return checkboxAnswerOptions.size() == Long.parseLong(ruleAnswer);
  }

  private boolean minSelected(String ruleAnswer, List<CheckboxAnswerOption> answer) {
    List<CheckboxAnswerOption> checkboxAnswerOptions = answer.stream().filter(checkboxAnswerOption -> checkboxAnswerOption.getState().equals(true)).collect(Collectors.toList());
    return checkboxAnswerOptions.size() >= Long.parseLong(ruleAnswer);
  }

  private boolean maxSelected(String ruleAnswer, List<CheckboxAnswerOption> answer) {
    List<CheckboxAnswerOption> checkboxAnswerOptions = answer.stream().filter(checkboxAnswerOption -> checkboxAnswerOption.getState().equals(true)).collect(Collectors.toList());
    return checkboxAnswerOptions.size() <= Long.parseLong(ruleAnswer);
  }
}
