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

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivityStatusQueryBuilder.class, FlagReportDaoBean.class})
public class FlagReportDaoBeanTest {
  private static final String CENTER = "MG";

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


    builder = PowerMockito.spy(new ActivityStatusQueryBuilder());
    PowerMockito
      .whenNew(ActivityStatusQueryBuilder.class)
      .withNoArguments()
      .thenReturn(builder);
  }

  @Test
  public void getActivitiesProgressReport_should_build_the_query_accordingly_with_center() {
    flagReportDaoBean.getActivitiesProgressReport(CENTER);


    Mockito.verify(builder, Mockito.times(1)).matchFieldCenter(CENTER);
    Mockito.verify(builder, Mockito.times(1)).projectLastStatus();
    Mockito.verify(builder, Mockito.times(1)).getStatusValue();
    Mockito.verify(builder, Mockito.times(1)).sortByDate();
    Mockito.verify(builder, Mockito.times(1)).removeStatusDate();
    Mockito.verify(builder, Mockito.times(1)).groupByParticipant();
    Mockito.verify(builder, Mockito.times(1)).projectId();
    Mockito.verify(builder, Mockito.times(1)).build();


  }

  @Test
  public void getActivitiesProgressReport_should_build_the_query_accordingly() {
    flagReportDaoBean.getActivitiesProgressReport();

    Mockito.verify(builder, Mockito.times(0)).matchFieldCenter(CENTER);
    Mockito.verify(builder, Mockito.times(1)).projectLastStatus();
    Mockito.verify(builder, Mockito.times(1)).getStatusValue();
    Mockito.verify(builder, Mockito.times(1)).sortByDate();
    Mockito.verify(builder, Mockito.times(1)).removeStatusDate();
    Mockito.verify(builder, Mockito.times(1)).groupByParticipant();
    Mockito.verify(builder, Mockito.times(1)).projectId();
    Mockito.verify(builder, Mockito.times(1)).build();
  }
}