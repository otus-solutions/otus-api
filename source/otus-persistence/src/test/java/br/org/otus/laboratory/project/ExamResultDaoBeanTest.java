package br.org.otus.laboratory.project;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

import br.org.otus.laboratory.project.builder.ExamResultQueryBuilder;

@Ignore
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
  public void getExamResultsExtractionValues_should_build_the_query() throws DataNotFoundException {
    this.examResultDaoBean.getExamResultsExtractionValues();

    Mockito.verify(builder, Mockito.times(1)).getExamResultsWithAliquotValid();
    Mockito.verify(builder, Mockito.times(1)).getSortingByExamName();
    Mockito.verify(builder, Mockito.times(1)).getSortingByRecruitmentNumber();
    Mockito.verify(builder, Mockito.times(1)).getGroupOfExamResultsToExtraction();
    Mockito.verify(builder, Mockito.times(1)).getProjectionOfExamResultsToExtraction();
    Mockito.verify(builder, Mockito.times(1)).build();
  }

}
