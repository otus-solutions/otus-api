package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.answer.DecimalAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
public class DecimalRuleValidatorServiceBeanTest {
    private DecimalRuleValidatorServiceBean decimalRuleValidatorServiceBean = new DecimalRuleValidatorServiceBean();

    @Spy
    private DecimalAnswer answer = new DecimalAnswer();
    private List<DecimalAnswer> decimalAnswerValues = new ArrayList();
    private Rule rule;

    @Before
    public void setUp() throws Exception {
        rule = new Rule();
        rule.extents = "SurveyTemplateObject";
        rule.objectType = "Rule";
        rule.when = "TST1";
        rule.isMetadata = false;
        setInternalState(answer, "value", 1.0);
        decimalAnswerValues.add(answer);
        setInternalState(answer, "objectType", "AnswerFill");
        setInternalState(answer, "type", "DecimalQuestion");
    }

    @Test
    public void run_method_with_equalCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "equal";
        rule.answer = String.valueOf(1.0);
        assertTrue(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_equalCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "equal";
        rule.answer = String.valueOf(2.0);
        assertFalse(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_notEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "notEqual";
        rule.answer = String.valueOf(2.0);
        assertTrue(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_notEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "notEqual";
        rule.answer = String.valueOf(1.0);
        assertFalse(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "greater";
        rule.answer = String.valueOf(0.9);
        assertTrue(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "greater";
        rule.answer = String.valueOf(1.1);
        assertFalse(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "greaterEqual";
        rule.answer = String.valueOf(1.0);
        assertTrue(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "greaterEqual";
        rule.answer = String.valueOf(1.1);
        assertFalse(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_lowerCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "lower";
        rule.answer = String.valueOf(1.1);
        assertTrue(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_lowerCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "lower";
        rule.answer = String.valueOf(0.9);
        assertFalse(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_lowerEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "lowerEqual";
        rule.answer = String.valueOf(1.0);
        assertTrue(decimalRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_lowerEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "lowerEqual";
        rule.answer = String.valueOf(0.9);
        assertFalse(decimalRuleValidatorServiceBean.run(rule, answer));
    }
}