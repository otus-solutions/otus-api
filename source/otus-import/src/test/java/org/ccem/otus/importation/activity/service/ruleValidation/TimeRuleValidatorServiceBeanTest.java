package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.answer.ImmutableDateAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
public class TimeRuleValidatorServiceBeanTest {

  TimeRuleValidatorServiceBean timeRuleValidatorServiceBean = new TimeRuleValidatorServiceBean();

  @Spy
  private ImmutableDateAnswer answer = new ImmutableDateAnswer();
  private Rule rule;
  private String type = "TimeQuestion";
  private ImmutableDate immutableDate = new ImmutableDate("1970-01-01 14:22:00.000");

  @Before
  public void setUp() throws Exception {
    rule = new Rule();
    rule.extents = "SurveyTemplateObject";
    rule.objectType = "Rule";
    rule.when = "TST1";
    rule.isMetadata = false;
    setInternalState(answer, "value", immutableDate);
  }

  @Test
  public void run_method_with_CalendarQuestion_and_equalCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    immutableDate = new ImmutableDate("1970-01-01 00:00:00.000");
    setInternalState(answer, "value", immutableDate);
    type = "CalendarQuestion";
    rule.operator = "equal";
    rule.answer = "01-01-1970";
    assertTrue(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void should_handle_exception_overriding_return() throws Exception {
    rule.operator = "equal";
    rule.answer = "01-01-0001";
    assertFalse(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_equalCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "equal";
    rule.answer = "14:22";
    assertTrue(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_equalCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "equal";
    rule.answer = "14:21";
    assertFalse(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_notEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "notEqual";
    rule.answer = "14:21";
    assertTrue(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_notEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "notEqual";
    rule.answer = "14:22";
    assertFalse(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_greaterCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "greater";
    rule.answer = "14:21";
    assertTrue(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_greaterCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "greater";
    rule.answer = "14:22";
    assertFalse(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_greaterEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "greaterEqual";
    rule.answer = "14:22";
    assertTrue(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_greaterEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "greaterEqual";
    rule.answer = "14:23";
    assertFalse(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_lowerCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "lower";
    rule.answer = "14:23";
    assertTrue(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_lowerCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "lower";
    rule.answer = "14:22";
    assertFalse(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_lowerEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "lowerEqual";
    rule.answer = "14:22";
    assertTrue(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test
  public void run_method_with_lowerEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
    rule.operator = "lowerEqual";
    rule.answer = "14:21";
    assertFalse(timeRuleValidatorServiceBean.run(type, rule, answer));
  }

  @Test(expected = DataNotFoundException.class)
  public void run_method_with_invalidCase_should_throws_DataNotFoundException() throws DataNotFoundException {
    rule.operator = "anyThing";
    rule.answer = "14:21";
    timeRuleValidatorServiceBean.run(type, rule, answer);
  }

}