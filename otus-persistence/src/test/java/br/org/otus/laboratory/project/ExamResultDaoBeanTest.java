package br.org.otus.laboratory.project;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
import com.mongodb.client.MongoCursor;

import br.org.otus.laboratory.project.builder.ExamResultQueryBuilder;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ExamResultQueryBuilder.class, ExamResultDaoBean.class })
public class ExamResultDaoBeanTest {

  @InjectMocks
  private ExamResultDaoBean examResultDaoBean;
  @Mock
  private MongoCollection<Document> collection;
  @Mock
  private ExamResultQueryBuilder builder;
  @Mock
  private AggregateIterable<Document> result;
  @Mock
  private MongoCursor<Document> iterator;
  @Mock
  private List<Bson> query;

  @Before
  public void setup() throws Exception {
    Whitebox.setInternalState(examResultDaoBean, "collection", collection);
    Mockito.when(this.collection.aggregate(query)).thenReturn(result);
    Mockito.when(result.iterator()).thenReturn(iterator);
    builder = PowerMockito.spy(new ExamResultQueryBuilder());
    PowerMockito.whenNew(ExamResultQueryBuilder.class).withNoArguments().thenReturn(builder);
  }

  @Test
  public void getExamResultsExtractionHeader_method_should_return_exception_DataNotFoundException_when_collection_is_empty() throws DataNotFoundException {

  }

  @Test
  public void getExamResultsExtractionHeader_method_should_call_distinct_method() throws DataNotFoundException {

  }

  @Test
  public void getExamResultsExtractionHeader_method_should_return_results_when_exam_results_list_of_headers_does_not_empty() {

  }

  @Test
  public void getExamResultsExtractionValues_method_should_return_exception_DataNotFoundException_when_collection_is_empty() {

  }

  @Ignore
  @Test
  public void getExamResultsExtractionValues_should_build_the_query() throws DataNotFoundException {
    examResultDaoBean.getExamResultsExtractionValues();

    Mockito.verify(builder, Mockito.times(1)).getExamResultsWithAliquotValid();
    Mockito.verify(builder, Mockito.times(1)).getSortingByExamName();
    Mockito.verify(builder, Mockito.times(1)).getExamResultsGroupByRecruitmentNumber();
    Mockito.verify(builder, Mockito.times(1)).getProjectionOfExamResultsToExtraction();
    Mockito.verify(builder, Mockito.times(1)).build();
  }

}
