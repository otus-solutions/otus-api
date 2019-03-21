package br.org.otus.survey.activity;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.permissions.service.user.group.UserPermission;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import br.org.mongodb.MongoGenericDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ActivityDaoBean.class })
public class ActivityDaoBeanTest {
	private static final String ACRONYM = "FORM";
	private static final Integer VERSION = 1;
	private static final String USER_EMAIL = "otus@tus.com";
	private static final Integer RECRUITMENT_NUMBER = 50000501;
	private static final List<String> LIST_SURVEYS = new ArrayList();

	@InjectMocks
	private ActivityDaoBean activityDaoBean = PowerMockito.spy(new ActivityDaoBean());

	@Mock
	private MongoGenericDao<Document> mongoGenericDao;
	@Mock
	private MongoCollection<Document> collection;
	@Mock
	private FindIterable<Document> result;
	@Mock
	private SurveyActivity surveyActivity;
	@Mock
	private ArrayList<SurveyActivity> activities;
		
	private MongoCursor cursor = PowerMockito.mock(MongoCursor.class);
	

	@Before
	public void setup() throws Exception {
		Document document = new Document("_id", new ObjectId("579397d20c2dd41b9a8a09eb")).append("surveyForm.surveyTemplate.identity.acronym", "CISE")
				.append("surveyForm.version", 1).append("isDiscarded", false);
		Whitebox.setInternalState(activityDaoBean, "collection", collection);
		Mockito.when(this.collection.find(Matchers.<Bson>any())).thenReturn(result);
		Mockito.when(result.projection(Matchers.<Bson>any())).thenReturn(result);
		Mockito.when(result.iterator()).thenReturn(cursor);
		Mockito.when(cursor.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(cursor.next()).thenReturn(document);

		PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(this.activities);
		PowerMockito.doReturn(1).when(this.activities).size();
		
		System.out.println();
	}

	@Test
	public void method_find_should_called_method_find() {
		activityDaoBean.find(LIST_SURVEYS, USER_EMAIL, RECRUITMENT_NUMBER);
		Mockito.verify(this.collection, Mockito.times(1)).find(Matchers.<Bson>any());
	}

	@Test
	public void method_get_should_return_list_not_null() throws Exception {
		Assert.assertNotNull(activityDaoBean.getUndiscarded(ACRONYM, VERSION));
	}

	@Test
	public void method_get_should_called_method_find() throws Exception {
		activityDaoBean.getUndiscarded(ACRONYM, VERSION);
		Mockito.verify(this.collection, Mockito.times(1)).find(Matchers.<Bson>any());
	}

	@Test
	public void method_get_should_return_exception_when_list_of_activities_is_empty()
			throws InterruptedException, DataNotFoundException, MemoryExcededException {
		PowerMockito.doReturn(0).when(activities).size();
		PowerMockito.doReturn(null).when(activityDaoBean).getUndiscarded(ACRONYM, VERSION);
		try {
			activityDaoBean.getUndiscarded(ACRONYM, VERSION);
		} catch (DataNotFoundException e) {
			assertTrue(e.getMessage().contains("OID {" + ACRONYM + "} not found."));
		}
	}

	@Test
	public void method_get_should_create_query_correct() throws Exception {
		Document query = new Document();
		query.put(ActivityDaoBean.ACRONYM_PATH, ACRONYM);
		query.put(ActivityDaoBean.VERSION_PATH, VERSION);
		query.put(ActivityDaoBean.DISCARDED_PATH, Boolean.FALSE);

		this.activityDaoBean.getUndiscarded(ACRONYM, VERSION);

		Mockito.verify(this.collection, Mockito.times(1)).find(query);
	}

}
