package br.org.otus.laboratory.project;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

import br.org.otus.laboratory.project.builder.ExamResultQueryBuilder;

@RunWith(PowerMockRunner.class)
public class ExamResultDaoBeanTest {

  @InjectMocks
  private ExamResultDaoBean examResultDaoBean;
  @Mock
  private MongoCollection<Document> collection;
  @Mock
  private ExamResultQueryBuilder builder;
  @Mock
  private AggregateIterable<Document> result;

  @Before
  public void setup() throws Exception {
    Mockito.when(collection.aggregate(Mockito.any())).thenReturn(result);
  }

  @Test
  public void getExamResultsExtractionHeader_method_should_call_distinct_method() throws DataNotFoundException {
    this.examResultDaoBean.getExamResultsExtractionHeaders();

    Mockito.verify(collection).distinct(Mockito.anyString(), Mockito.any());
  }

  @Test(expected = DataNotFoundException.class)
  public void getExamResultsExtractionValues_method_should_return_exception_DataNotFoundException_when_collection_is_empty() throws DataNotFoundException {
    this.examResultDaoBean.getExamResultsExtractionHeaders();
  }

  @Test
  public void getExamResultsExtractionValues_should_build_the_query() throws DataNotFoundException {
    this.examResultDaoBean.getExamResultsExtractionValues();

    Mockito.verify(builder, Mockito.times(1)).getExamResultsWithAliquotValid();
    Mockito.verify(builder, Mockito.times(1)).getSortingByExamName();
    Mockito.verify(builder, Mockito.times(1)).getExamResultsGroupByRecruitmentNumber();
    Mockito.verify(builder, Mockito.times(1)).getProjectionOfExamResultsToExtraction();
    Mockito.verify(builder, Mockito.times(1)).build();
  }

}
