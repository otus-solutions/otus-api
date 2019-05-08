package org.ccem.otus.importation.activity.service;

import org.ccem.otus.importation.activity.service.ruleValidation.*;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.filling.answer.CheckboxAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.CheckboxAnswerOption;
import org.ccem.otus.survey.template.navigation.route.Rule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
public class RuleRunnerServiceBeanTest {

    @InjectMocks
    private RuleRunnerServiceBean ruleRunnerServiceBean;
    @Mock
    private IntegerRuleValidatorService integerRuleValidatorService;
    @Mock
    private DecimalRuleValidatorService decimalRuleValidatorService;
    @Mock
    private TextRuleValidatorService textRuleValidatorService;
    @Mock
    private TimeRuleValidatorService timeRuleValidatorService;
    @Mock
    private CheckboxRuleValidatorService checkboxRuleValidatorService;
    @Spy
    private CheckboxAnswer answer = new CheckboxAnswer();
    @Spy
    private CheckboxAnswerOption checkboxAnswerOption = new CheckboxAnswerOption();
    @Spy
    private QuestionFill ruleQuestionFill = new QuestionFill();
    private List<CheckboxAnswerOption> checkboxAnswerOptions = new ArrayList();
    private Rule rule;

    @Before
    public void setUp() throws Exception {
        rule = new Rule();
        rule.extents = "SurveyTemplateObject";
        rule.objectType = "Rule";
        rule.when = "TST1";
        rule.isMetadata = false;
        rule.operator = "equal";
        rule.answer = "B";

        setInternalState(checkboxAnswerOption, "option", "B");
        setInternalState(checkboxAnswerOption, "state", true);
        checkboxAnswerOptions.add(checkboxAnswerOption);
        setInternalState(answer, "objectType", "AnswerFill");
        setInternalState(answer, "value", checkboxAnswerOptions);

        setInternalState(ruleQuestionFill, "objectType", "QuestionFill");
        setInternalState(ruleQuestionFill, "questionID", "TST1");
        setInternalState(ruleQuestionFill, "answer", answer);
        setInternalState(ruleQuestionFill, "forceAnswer", false);
    }

    @Test
    public void run_method_should_not_access_the_answerTypes_caseSwitch() {
        rule.isMetadata = true;
        when(integerRuleValidatorService.run(rule, answer)).thenReturn(false);
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
    }

    @Test
    public void run_method_should_access_default_by_answerTypes_caseSwitch() {
        when(answer.getType()).thenReturn("anyThing");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_integerRuleValidator_in_case_IntegerQuestion() {
        when(answer.getType()).thenReturn("IntegerQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(integerRuleValidatorService, times(1)).run(rule, answer);
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_integerRuleValidator_in_case_SingleSelectionQuestion() {
        when(answer.getType()).thenReturn("SingleSelectionQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(integerRuleValidatorService, times(1)).run(rule, answer);
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_decimalRuleValidator() {
        when(answer.getType()).thenReturn("DecimalQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(decimalRuleValidatorService, times(1)).run(rule, answer);
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_textRuleValidator_in_case_TextQuestion() {
        when(answer.getType()).thenReturn("TextQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(textRuleValidatorService, times(1)).run(rule, answer);
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_textRuleValidator_in_case_EmailQuestion() {
        when(answer.getType()).thenReturn("EmailQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(textRuleValidatorService, times(1)).run(rule, answer);
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_textRuleValidator_in_case_AutocompleteQuestion() {
        when(answer.getType()).thenReturn("AutocompleteQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(textRuleValidatorService, times(1)).run(rule, answer);
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_timeRuleValidator_in_case_TimeQuestion() {
        when(answer.getType()).thenReturn("TimeQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(timeRuleValidatorService, times(1)).run(answer.getType(), rule, answer);
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_timeRuleValidator_in_case_CalendarQuestion() {
        when(answer.getType()).thenReturn("CalendarQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(timeRuleValidatorService, times(1)).run(answer.getType(), rule, answer);
    }

    @Test
    public void run_method_should_evoke_run_by_service_of_checkboxRuleValidator() {
        when(answer.getType()).thenReturn("CheckboxQuestion");
        assertFalse(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
        verify(checkboxRuleValidatorService, times(1)).run(rule, answer);
    }

    @Test
    public void run_method_should_simulate_return_positive_by_run_of_checkboxRuleValidatorService() {
        when(answer.getType()).thenReturn("CheckboxQuestion");
        when(checkboxRuleValidatorService.run(rule, answer)).thenReturn(true);
        assertTrue(ruleRunnerServiceBean.run(rule, Optional.of(ruleQuestionFill)));
    }
}