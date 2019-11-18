package br.org.otus.extraction;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.ActivityInapplicabilityDao;
import org.ccem.otus.persistence.SurveyDao;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import br.org.otus.extraction.builder.ActivityProgressExtractionQueryBuilder;

@Ignore
@RunWith(PowerMockRunner.class)
public class ActivityProgressExtractionDaoBeanTest {

  private static final String CENTER = "RS";
  private static LinkedList<String> SURVEY_ACRONYM_LIST = new LinkedList<>();
  private MongoCursor cursor = PowerMockito.mock(MongoCursor.class);
  
  @InjectMocks
  private ActivityProgressExtractionDaoBean activityProgressExtractionDaoBean = PowerMockito.spy(new ActivityProgressExtractionDaoBean());
  @Mock
  private SurveyDao surveyDao;
  @Mock
  private ParticipantDao participantDao;
  @Mock
  private ActivityInapplicabilityDao activityInapplicabilityDao;
  @Mock
  private MongoCollection<Document> collection;
  @Mock
  private AggregateIterable<Document> aggregateResultMock;
  @Mock
  private ArrayList<Bson> queryResult;
  @Mock
  private ActivityProgressExtractionQueryBuilder builder;
  @Mock
  private AggregateIterable<Document> result;

  @Before
  public void setup() throws Exception {
    Document document = new Document("rn", "5006259")
        .append("status", "FINALIZED")
        .append("acronym", "CSJ")
        .append("statusDate", "2018-10-15T11:40:05.282Z")
        .append("observation", "");
    
    Whitebox.setInternalState(activityProgressExtractionDaoBean, "collection", collection);
    when(surveyDao.listAcronyms()).thenReturn(SURVEY_ACRONYM_LIST);
    when(participantDao.aggregate(Matchers.anyList())).thenReturn(aggregateResultMock);
    when(activityInapplicabilityDao.aggregate(Matchers.anyList())).thenReturn(aggregateResultMock);
    when(aggregateResultMock.allowDiskUse(true)).thenReturn(aggregateResultMock);
    when(aggregateResultMock.first()).thenReturn(new Document());
    
    when(collection.aggregate(Matchers.anyList())).thenReturn(result);
    when(result.allowDiskUse(true)).thenReturn(result);
    when(result.iterator()).thenReturn(cursor);
    when(cursor.hasNext()).thenReturn(true).thenReturn(false);
    when(cursor.next()).thenReturn(document);
    
    builder = PowerMockito.spy(new ActivityProgressExtractionQueryBuilder());
    PowerMockito.whenNew(ActivityProgressExtractionQueryBuilder.class).withNoArguments().thenReturn(builder);
  }

  @Test
  public void getActivityProgressExtraction_method_should_return_instance_of_list() throws DataNotFoundException {
    Assert.assertTrue(activityProgressExtractionDaoBean.getActivityProgressExtraction(CENTER) instanceof LinkedList);
  }
  
  @Test
  public void getActivityProgressExtraction_method_should_return_expected_values() throws DataNotFoundException {
    LinkedList<ActivityProgressResultExtraction> result = activityProgressExtractionDaoBean.getActivityProgressExtraction(CENTER);
    
    Assert.assertEquals("5006259", result.get(0).getRecruitmentNumber().toString());
    Assert.assertEquals("FINALIZED", result.get(0).getStatus());
    Assert.assertEquals("CSJ", result.get(0).getAcronym());
    Assert.assertEquals("2018-10-15T11:40:05.282Z", result.get(0).getStatusDate());
    Assert.assertEquals("", result.get(0).getInapplicabilityObservation());
  }

}