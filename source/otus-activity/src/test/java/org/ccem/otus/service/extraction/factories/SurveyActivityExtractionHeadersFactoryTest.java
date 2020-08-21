package org.ccem.otus.service.extraction.factories;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.ccem.otus.survey.template.item.questions.numeric.NumericQuestion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class SurveyActivityExtractionHeadersFactoryTest {

  private SurveyActivityExtractionHeadersFactory factory;
  private NumericQuestion numericQuestion;
  private List<SurveyItem> itemContainer;
  private SurveyTemplate surveyTemplate;
  private SurveyForm surveyForm;

  @Before
  public void setup() {
    this.numericQuestion = new NumericQuestion();
    Whitebox.setInternalState(this.numericQuestion, "customID", "FORM1");
    Whitebox.setInternalState(this.numericQuestion, "templateID", "FORM1");
    this.itemContainer = new ArrayList<>();
    this.itemContainer.add(this.numericQuestion);
    this.surveyTemplate = new SurveyTemplate();
    Whitebox.setInternalState(this.surveyTemplate, "itemContainer", this.itemContainer);
    this.surveyForm = new SurveyForm(this.surveyTemplate, "test@test.com");

    this.factory = new SurveyActivityExtractionHeadersFactory(this.surveyForm);
  }

  @Test
  public void getHeaders_method_should_return_instance_of_list() {
    List<String> values = this.factory.getHeaders();

    Assert.assertTrue(values instanceof List);
  }

  @Test
  public void getHeaders_method_should_return_list_with_values_expected() {
    /* Basic information headers */
    Assert.assertEquals("recruitment_number", this.factory.getHeaders().get(0));
    Assert.assertEquals("participant_field_center", this.factory.getHeaders().get(1));
    Assert.assertEquals("acronym", this.factory.getHeaders().get(2));
    Assert.assertEquals("mode", this.factory.getHeaders().get(3));
    Assert.assertEquals("type", this.factory.getHeaders().get(4));
    Assert.assertEquals("category", this.factory.getHeaders().get(5));
    Assert.assertEquals("participant_field_center_by_activity", this.factory.getHeaders().get(6));
    Assert.assertEquals("interviewer", this.factory.getHeaders().get(7));
    Assert.assertEquals("current_status", this.factory.getHeaders().get(8));
    Assert.assertEquals("current_status_date", this.factory.getHeaders().get(9));
    Assert.assertEquals("creation_date", this.factory.getHeaders().get(10));
    Assert.assertEquals("paper_realization_date", this.factory.getHeaders().get(11));
    Assert.assertEquals("paper_interviewer", this.factory.getHeaders().get(12));
    Assert.assertEquals("last_finalization_date", this.factory.getHeaders().get(13));
    Assert.assertEquals("external_ID", this.factory.getHeaders().get(14));
    /* Answers headers */
    Assert.assertEquals("FORM1", this.factory.getHeaders().get(15));
  }

}
