package br.org.otus.survey.activity;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
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

import br.org.mongodb.MongoGenericDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ActivityDaoBean.class })
public class ActivityDaoBeanTest {
	private static final String ACRONYM = "FORM";
	private static final Integer VERSION = 1;

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

	@Before
	public void setup() throws Exception {
		Whitebox.setInternalState(activityDaoBean, "collection", collection);
		Mockito.when(this.collection.find(Matchers.<Bson>any())).thenReturn(this.result);
		Mockito.when(result.projection(Matchers.<Bson>any())).thenReturn(result);

		PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(this.activities);
		PowerMockito.doReturn(1).when(this.activities).size();
	}

	@Test
	public void method_get_should_return_list_not_null() throws Exception {
		Assert.assertNotNull(this.activityDaoBean.getUndiscarded(ACRONYM, VERSION));
	}

	@Test
	public void method_get_should_called_method_find() throws Exception {
		this.activityDaoBean.getUndiscarded(ACRONYM, VERSION);

		Mockito.verify(this.collection, Mockito.times(1)).find(Matchers.<Bson>any());
	}

	@Test
	public void method_get_should_return_exception_when_list_of_activities_is_empty() {
		PowerMockito.doReturn(0).when(this.activities).size();
		try {
			this.activityDaoBean.getUndiscarded(ACRONYM, VERSION);
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
