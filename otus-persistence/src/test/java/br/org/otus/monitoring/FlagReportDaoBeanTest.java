package br.org.otus.monitoring;

import br.org.otus.monitoring.builder.ActivityStatusQueryBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
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

import java.util.LinkedList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivityStatusQueryBuilder.class, FlagReportDaoBean.class})
public class FlagReportDaoBeanTest {
  private static final String CENTER = "MG";
  private static LinkedList<String> SURVEY_ACRONYM_LIST = new LinkedList<>();

  @InjectMocks
  private FlagReportDaoBean flagReportDaoBean;

  @Mock
  private MongoCollection<Document> collection;

  @Mock
  private AggregateIterable<Document> result;

  @Mock
  private ActivityStatusQueryBuilder builder;

  @Mock
  private MongoCursor<Document> iterator;

  @Before
  public void setUp() throws Exception {
    Whitebox.setInternalState(flagReportDaoBean, "collection", collection);
    Mockito.when(this.collection.aggregate(Matchers.anyList())).thenReturn(result);
    Mockito.when(result.iterator()).thenReturn(iterator);

    SURVEY_ACRONYM_LIST = new LinkedList<>();
    SURVEY_ACRONYM_LIST.add("HVSD");
    SURVEY_ACRONYM_LIST.add("PSEC");
    SURVEY_ACRONYM_LIST.add("ABC");
    SURVEY_ACRONYM_LIST.add("DEF");
    builder = PowerMockito.spy(new ActivityStatusQueryBuilder());
    PowerMockito
      .whenNew(ActivityStatusQueryBuilder.class)
      .withNoArguments()
      .thenReturn(builder);
  }

  @Test
  public void getActivitiesProgressReport_should_build_the_query_accordingly_with_center() {
    flagReportDaoBean.getActivitiesProgressReport(SURVEY_ACRONYM_LIST);
    Mockito.verify(builder, Mockito.times(1)).getActivityStatusQuery(SURVEY_ACRONYM_LIST);
  }

  @Test
  public void getActivitiesProgressReport_should_build_the_query_accordingly() {
    flagReportDaoBean.getActivitiesProgressReport(CENTER,SURVEY_ACRONYM_LIST);

    Mockito.verify(builder, Mockito.times(1)).getActivityStatusQuery(CENTER,SURVEY_ACRONYM_LIST);
  }
}