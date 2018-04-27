package br.org.otus.survey.activity;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.junit.Before;
import org.junit.Ignore;
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

	private static final String COLLECTION_NAME = "activity";
	private static final String QUERY_TO_GET_ACTIVITIES_TO_EXTRACTION = "{{$match=Document{{surveyForm.surveyTemplate.identity.acronym=CSJ, isDiscarded=false}}}}";
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
	public void setup() {
		this.query = new ArrayList<>();
		this.query.add(new Document("$match", new Document("surveyForm.surveyTemplate.identity.acronym", ACRONYM).append("isDiscarded", Boolean.FALSE)));
	}

	@Test
	public void method_getActivitiesToExtraction_should_return_list_of_SurveyActivity() throws Exception {
		Whitebox.setInternalState(activityDaoBean, "collection", collection);
		Mockito.when(this.collection.aggregate(Mockito.anyList())).thenReturn(this.result);

		PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(this.activities);
		PowerMockito.doReturn(1).when(this.activities).size();

		this.activityDaoBean.getActivitiesToExtraction(ACRONYM);

		// TODO:
	}

	@Test
	public void method_getActivitiesToExtraction_should_called_method_aggregate() throws Exception {
		Whitebox.setInternalState(activityDaoBean, "collection", collection);
		Mockito.when(this.collection.aggregate(Mockito.anyList())).thenReturn(this.result);

		PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(this.activities);
		PowerMockito.doReturn(1).when(this.activities).size();

		this.activityDaoBean.getActivitiesToExtraction(ACRONYM);

		Mockito.verify(this.collection, Mockito.times(1)).aggregate(Mockito.anyList());
	}

	@Test
	public void method_getActivitiesToExtraction_should_return_create_query_correct_with_acronym_and_discarded_how_false() throws Exception {
		Whitebox.setInternalState(activityDaoBean, "collection", collection);
		Mockito.when(this.collection.aggregate(Mockito.anyList())).thenReturn(this.result);

		PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(this.activities);
		PowerMockito.doReturn(1).when(this.activities).size();

		List<SurveyActivity> extraction = this.activityDaoBean.getActivitiesToExtraction(ACRONYM);

		// TODO:

	}

	@Ignore
	@Test
	public void method_getActivitiesToExtraction_should_return_only_not_discarded_activities() throws Exception {
		Whitebox.setInternalState(activityDaoBean, "collection", collection);
		Mockito.when(this.collection.aggregate(Mockito.anyList())).thenReturn(this.result);

		PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(this.activities);
		PowerMockito.doReturn(1).when(this.activities).size();

		List<SurveyActivity> extraction = this.activityDaoBean.getActivitiesToExtraction(ACRONYM);

		// TODO:
	}

}
