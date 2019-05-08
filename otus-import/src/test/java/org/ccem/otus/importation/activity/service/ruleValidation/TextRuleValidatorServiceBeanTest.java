package org.ccem.otus.importation.activity.service.ruleValidation;

import org.ccem.otus.model.survey.activity.filling.answer.TextAnswer;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
public class TextRuleValidatorServiceBeanTest {
    private TextRuleValidatorServiceBean textRuleValidatorServiceBean = new TextRuleValidatorServiceBean();
    @Spy
    private TextAnswer answer = new TextAnswer();
    private Rule rule;

    @Before
    public void setUp() throws Exception {
        rule = new Rule();
        rule.extents = "SurveyTemplateObject";
        rule.objectType = "Rule";
        rule.when = "TST1";
        rule.isMetadata = false;
        setInternalState(answer, "value", "Otus");
        setInternalState(answer, "objectType", "AnswerFill");
        setInternalState(answer, "type", "TextQuestion");
    }

    @Test
    public void run_method_with_equalCase_should_validate_the_comparison_of_values_between_answer_and_rule() {
        rule.operator = "equal";
        rule.answer = "Otus";
        assertTrue(textRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_equalCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() {
        rule.operator = "equal";
        rule.answer = "otus";
        assertFalse(textRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_notEqualCase_should_validate_the_comparison_of_values_between_answer_and_rule() {
        rule.operator = "notEqual";
        rule.answer = "otus";
        assertTrue(textRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_notEqualCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() {
        rule.operator = "notEqual";
        rule.answer = "Otus";
        assertFalse(textRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterCase_should_validate_the_comparison_of_values_between_answer_and_rule() {
        rule.operator = "greater";
        rule.answer = "Otu";
        assertTrue(textRuleValidatorServiceBean.run(rule, answer));
    }

    @Test
    public void run_method_with_greaterCase_should_invalidate_the_comparison_of_values_between_answer_and_rule() {
        rule.operator = "greater";
        rule.answer = "otu";
        assertFalse(textRuleValidatorServiceBean.run(rule, answer));
    }
}