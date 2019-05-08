package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.model.survey.activity.filling.answer.CheckboxAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.CheckboxAnswerOption;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
public class CheckboxRuleValidatorServiceBeanTest {

    private CheckboxRuleValidatorServiceBean checkboxRuleValidatorServiceBean = new CheckboxRuleValidatorServiceBean();
    @Spy
    private CheckboxAnswer answer = new CheckboxAnswer();
    @Spy
    private CheckboxAnswerOption checkboxAnswerOption = new CheckboxAnswerOption();
    private List<CheckboxAnswerOption> checkboxAnswerValues = new ArrayList();
    private Rule rule;

    @Before
    public void setUp() throws Exception {
        rule = new Rule();
        rule.extents = "SurveyTemplateObject";
        rule.objectType = "Rule";
        rule.when = "TST1";
        rule.isMetadata = false;
        setInternalState(checkboxAnswerOption, "option", "B");
        setInternalState(checkboxAnswerOption, "state", true);
        checkboxAnswerValues.add(checkboxAnswerOption);
        setInternalState(answer, "objectType", "AnswerFill");
        setInternalState(answer, "type", "CheckboxQuestion");
        setInternalState(answer, "value", checkboxAnswerValues);
    }

    @Test
    public void run_method_should_deliver_positive_results_in_the_event_that_isEqual_has_any_items_on_the_list() {
        rule.operator = "equal";
        rule.answer = "B";
        assertTrue(checkboxRuleValidatorServiceBean.run(rule, answer));
        verify(checkboxAnswerOption, Mockito.times(1)).getOption();
        verify(checkboxAnswerOption, Mockito.times(1)).getState();
    }

    @Test
    public void run_method_should_deliver_negative_results_in_the_event_that_isEqual_not_has_any_items_on_the_list() {
        rule.operator = "equal";
        rule.answer = "A";
        assertFalse(checkboxRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_should_deliver_positive_results_in_the_event_that_notEqual_has_any_items_on_the_list() {
        rule.operator = "notEqual";
        rule.answer = "A";
        assertTrue(checkboxRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_should_deliver_negative_results_in_the_event_that_notEqual_not_has_any_items_on_the_list() {
        rule.operator = "notEqual";
        rule.answer = "B";
        assertFalse(checkboxRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_should_deliver_positive_results_in_the_event_that_quantity_has_any_items_on_the_list() {
        rule.operator = "quantity";
        rule.answer = String.valueOf(1);
        assertTrue(checkboxRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_should_deliver_negative_results_in_the_event_that_quantity__not_has_any_items_on_the_list() {
        rule.operator = "quantity";
        rule.answer = String.valueOf(0);
        assertFalse(checkboxRuleValidatorServiceBean.run(rule, answer));
    }

        @Test
    public void run_method_should_deliver_positive_results_in_the_event_that_minSelected_has_any_items_on_the_list() {
        rule.operator = "minSelected";
        rule.answer = "1";
        assertTrue(checkboxRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_should_deliver_negative_results_in_the_event_that_minSelected_not_has_any_items_on_the_list() {
        rule.operator = "minSelected";
        rule.answer = "2";
        assertFalse(checkboxRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_should_deliver_positive_results_in_the_event_that_maxSelected_has_any_items_on_the_list() {
        rule.operator = "maxSelected";
        rule.answer = "1";
        assertTrue(checkboxRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_should_deliver_negative_results_in_the_event_that_maxSelected_not_has_any_items_on_the_list() {
        rule.operator = "maxSelected";
        rule.answer = String.valueOf(0);
        assertFalse(checkboxRuleValidatorServiceBean.run(rule, answer));
    }
}