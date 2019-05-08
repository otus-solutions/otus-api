package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.filling.answer.IntegerAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.TextAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class IntegerRuleValidatorServiceBeanTest {
    private static final String VALUE ="3";

    @InjectMocks
    private IntegerRuleValidatorServiceBean integerRuleValidatorServiceBean = new IntegerRuleValidatorServiceBean();

    @Spy
    private TextAnswer answer = new TextAnswer();
    @Spy
    private IntegerAnswer integerAnswer = new IntegerAnswer();
    private List<IntegerAnswer> integerAnswersValues = new ArrayList();
    private List<TextAnswer> textAnswersValues = new ArrayList();
    private Rule rule;

    private DataNotFoundException e = spy(new DataNotFoundException());

    @Before
    public void setUp() throws Exception {
        rule = new Rule();
        rule.extents = "SurveyTemplateObject";
        rule.objectType = "Rule";
        rule.when = "TST1";
        rule.isMetadata = false;
        setInternalState(answer, "value",VALUE);
        textAnswersValues.add(answer);
        setInternalState(answer, "objectType", "AnswerFill");
        setInternalState(answer, "type", "SingleSelectionQuestion");
    }

    @Test
    public void run_method_with_equalCase_should_validate_for_IntegerAnswer() throws DataNotFoundException {
        setInternalState(integerAnswer, "value",Long.parseLong(VALUE));
        integerAnswersValues.add(integerAnswer);
        setInternalState(integerAnswer, "objectType", "AnswerFill");
        setInternalState(integerAnswer, "type", "DecimalQuestion");
        rule.operator = "equal";
        rule.answer = String.valueOf(3);
        assertTrue(integerRuleValidatorServiceBean.run(rule, integerAnswer));
    }

    @Test (expected = DataNotFoundException.class)
    public void run_method_should_throw_DataNotFoundException() throws DataNotFoundException {
        rule.operator = "equalNot";
        rule.answer = String.valueOf(3);
        when(integerRuleValidatorServiceBean.run(rule, answer)).thenThrow(e);
    }

    @Test
    public void run_method_with_equalCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "equal";
        rule.answer = String.valueOf(3);
        assertTrue(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_equalCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "equal";
        rule.answer = String.valueOf(2);
        assertFalse(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_notEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule()  throws DataNotFoundException {
        rule.operator = "notEqual";
        rule.answer = String.valueOf(2);
        assertTrue(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_notEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule()  throws DataNotFoundException {
        rule.operator = "notEqual";
        rule.answer = String.valueOf(3);
        assertFalse(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "greater";
        rule.answer = String.valueOf(1);
        assertTrue(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "greater";
        rule.answer = String.valueOf(4);
        assertFalse(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "greaterEqual";
        rule.answer = String.valueOf(3);
        assertTrue(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "greaterEqual";
        rule.answer = String.valueOf(4);
        assertFalse(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_lowerCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "lower";
        rule.answer = String.valueOf(4);
        assertTrue(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_lowerCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "lower";
        rule.answer = String.valueOf(2);
        assertFalse(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_lowerEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "lowerEqual";
        rule.answer = String.valueOf(3);
        assertTrue(integerRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_lowerEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() throws DataNotFoundException {
        rule.operator = "lowerEqual";
        rule.answer = String.valueOf(2);
        assertFalse(integerRuleValidatorServiceBean.run(rule, answer));
    }
}