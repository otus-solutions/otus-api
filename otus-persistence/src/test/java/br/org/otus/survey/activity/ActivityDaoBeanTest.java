package br.org.otus.survey.activity;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

import br.org.mongodb.MongoGenericDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ActivityDaoBean.class })
public class ActivityDaoBeanTest {

	private static final String ACRONYM = "FORM";

	@InjectMocks
	private ActivityDaoBean activityDaoBean = PowerMockito.spy(new ActivityDaoBean());

	@Mock
	private MongoGenericDao<Document> mongoGenericDao;
	@Mock
	private MongoCollection<Document> collection;
	@Mock
	private AggregateIterable<Document> result;
	@Mock
	private SurveyActivity surveyActivity;
	@Mock
	private ArrayList<SurveyActivity> activities;

	private ArrayList<Document> query;

	@Before
	public void setup() throws Exception {
		this.query = new ArrayList<>();
		this.query.add(new Document("$match", new Document("surveyForm.surveyTemplate.identity.acronym", ACRONYM).append("isDiscarded", Boolean.FALSE)));

		Whitebox.setInternalState(activityDaoBean, "collection", collection);
		Mockito.when(this.collection.aggregate(Mockito.anyList())).thenReturn(this.result);

		PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(this.activities);
		PowerMockito.doReturn(1).when(this.activities).size();
	}

	@Test
	public void method_getActivitiesToExtraction_should_return_list_not_null() throws Exception {

		Assert.assertNotNull(this.activityDaoBean.getActivitiesToExtraction(ACRONYM));
	}

	@Test
	public void method_getActivitiesToExtraction_should_called_method_aggregate() throws Exception {
		this.activityDaoBean.getActivitiesToExtraction(ACRONYM);

		Mockito.verify(this.collection, Mockito.times(1)).aggregate(Mockito.anyList());
	}

	@Test
	public void method_getActivitiesToExtraction_should_return_exception_when_list_of_activities_is_empty() {
		PowerMockito.doReturn(0).when(this.activities).size();
		try {
			this.activityDaoBean.getActivitiesToExtraction(ACRONYM);
		} catch (DataNotFoundException e) {
			assertTrue(e.getMessage().contains("OID {" + ACRONYM + "} not found."));
		}
	}

	@Test
	public void method_getActivitiesToExtraction_should_create_query_correct_with_acronym_and_discarded_how_false() throws Exception {
		Document query = new Document("$match", new Document("surveyForm.surveyTemplate.identity.acronym", ACRONYM).append("isDiscarded", Boolean.FALSE));
		Document result = Whitebox.invokeMethod(this.activityDaoBean, "buildQueryToExtraction", ACRONYM);
		this.activityDaoBean.getActivitiesToExtraction(ACRONYM);

		Assert.assertEquals(query, result);
	}
}
