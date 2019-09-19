package org.ccem.otus.model.survey.activity.filling;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QuestionFillTest {

	private static final String QUESTION_ID = "ExampleID";
	private static final String EXAMPLE_METADATA = "ExampleMetadata";

	private QuestionFill questionFill;
	private HashMap<String, Object> answerExtract;

	@Before
	public void setup() {
		questionFill = new QuestionFill();
		answerExtract = new HashMap<String, Object>();
	}

	@Test
	@Ignore
	public void method_extraction_should_return_instanceof_ExtractionFill() {
		AnswerFill answerFill = mock(AnswerFill.class);
		when(answerFill.getAnswerExtract(QUESTION_ID)).thenReturn(answerExtract);

		MetadataFill metadataFill = mock(MetadataFill.class);
		when(metadataFill.getValue()).thenReturn(EXAMPLE_METADATA);

		Assert.assertTrue(questionFill.extraction() instanceof ExtractionFill);
	}

}