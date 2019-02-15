package br.org.otus.monitoring.laboratory;

import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.monitoring.builder.LaboratoryProgressQueryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LaboratoryProgressQueryBuilder.class, LaboratoryProgressDao.class})
public class LaboratoryProgressDaoBeanTest {
    private static Gson gsonBuilder = new GsonBuilder().create();
    private static String CENTER = "RS";

    @InjectMocks
    private LaboratoryProgressDaoBean laboratoryProgressDaoBean;

    @Mock
    private LaboratoryProgressQueryBuilder laboratoryProgressQueryBuilder;

    @Mock
    private AliquotDao aliquotDao;

    @Mock
    private ExamResultDao examResultDao;

    @Mock
    private AggregateIterable<Document> result;

    @Mock
    private AggregateIterable<Document> result2;

    @Mock
    private AggregateIterable<Document> result3;

    @Mock
    private AggregateIterable<Document> result4;

    @Mock
    List<Bson> query1;

    @Mock
    List<Bson> query2;

    @Mock
    List<Bson> query3;

    @Mock
    List<Bson> query4;

    @Test
    public void getDataOrphanByExams_should_call_examResultDaoAggregate_with_OrphansQuery() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getOrphansQuery();
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document());
        laboratoryProgressDaoBean.getDataOrphanByExams();
        verifyPrivate(laboratoryProgressDaoBean).invoke("examResultDaoAggregate",query1);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataOrphanByExams_should_throw_DataNotFoundException() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getOrphansQuery();
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(null);
        laboratoryProgressDaoBean.getDataOrphanByExams();
    }

    @Test
    public void getDataOrphanByExams_should_return_LaboratoryProgressDTO_with_orphanExamsProgress() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getOrphansQuery();
        String orphanExamsProgress = gsonBuilder.toJson(new Document("orphanExamsProgress", Arrays.asList()));
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("orphanExamsProgress", Arrays.asList()));
        assertEquals(orphanExamsProgress,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataOrphanByExams()));
    }

    @Test
    public void getDataQuantitativeByTypeOfAliquots_should_execute_queries() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getQuantitativeByTypeOfAliquotsFirstPartialResultQuery(CENTER);
        query2 = new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesQuery(CENTER);
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query3 = new LaboratoryProgressQueryBuilder().getAliquotCodesInExamLotQuery(aliquotCodes);
        query4 = new LaboratoryProgressQueryBuilder().getQuantitativeByTypeOfAliquotsSecondPartialResultQuery(aliquotCodes);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(aliquotDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("quantitativeByTypeOfAliquots", Arrays.asList()));
        when(aliquotDao.aggregate(query2)).thenReturn(result2);
        when(result2.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        when(examResultDao.aggregate(query3)).thenReturn(result3);
        when(result3.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        when(aliquotDao.aggregate(query4)).thenReturn(result4);
        when(result4.first()).thenReturn(new Document("aliquotCodes", Arrays.asList()));
        laboratoryProgressDaoBean.getDataQuantitativeByTypeOfAliquots(CENTER);
        Mockito.verify(aliquotDao, Mockito.times(1)).aggregate(query1);
        Mockito.verify(aliquotDao, Mockito.times(1)).aggregate(query2);
        Mockito.verify(examResultDao, Mockito.times(1)).aggregate(query3);
        Mockito.verify(aliquotDao, Mockito.times(1)).aggregate(query4);
    }

    @Test
    public void getDataOfPendingResultsByAliquot_should_execute_queries() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query2 = new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotFirstPartialResultQuery(aliquotCodes,CENTER);
        query3 = new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotSecondPartialResultQuery(aliquotCodes,CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        when(aliquotDao.aggregate(query2)).thenReturn(result2);
        when(result2.first()).thenReturn(new Document("pendingResultsByAliquot",  Arrays.asList()));
        when(aliquotDao.aggregate(query3)).thenReturn(result3);
        when(result3.first()).thenReturn(new Document("pendingResultsByAliquot",  Arrays.asList()));
        laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER);
        Mockito.verify(examResultDao, Mockito.times(1)).aggregate(query1);
        Mockito.verify(aliquotDao, Mockito.times(1)).aggregate(query2);
        Mockito.verify(aliquotDao, Mockito.times(1)).aggregate(query3);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataOfPendingResultsByAliquot_should_throw_DataNotFoundException_on_first_query_fail() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataOfPendingResultsByAliquot_should_throw_DataNotFoundException_when_first_query_return_null() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(null);
        laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataOfPendingResultsByAliquot_should_throw_DataNotFoundException_on_second_query_fail() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query2 = new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotFirstPartialResultQuery(aliquotCodes,CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataOfPendingResultsByAliquot_should_throw_DataNotFoundException_when_second_query_return_null() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query2 = new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotFirstPartialResultQuery(aliquotCodes,CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        when(aliquotDao.aggregate(query2)).thenReturn(result2);
        when(result2.first()).thenReturn(null);
        laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataOfPendingResultsByAliquot_should_throw_DataNotFoundException_on_third_query_fail() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query2 = new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotFirstPartialResultQuery(aliquotCodes,CENTER);
        query3 = new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotSecondPartialResultQuery(aliquotCodes,CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        when(aliquotDao.aggregate(query2)).thenReturn(result2);
        when(result2.first()).thenReturn(new Document("pendingResultsByAliquot",  Arrays.asList()));
        laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER);
    }

    @Test
    public void getDataByExam_should_execute_queries() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesQuery(CENTER);
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query2 =new LaboratoryProgressQueryBuilder().getDataByExamQuery(aliquotCodes);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(aliquotDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        when(examResultDao.aggregate(query2)).thenReturn(result2);
        when(result2.first()).thenReturn(new Document("pendingResultsByAliquot",  Arrays.asList()));
        laboratoryProgressDaoBean.getDataByExam(CENTER);
        Mockito.verify(aliquotDao, Mockito.times(1)).aggregate(query1);
        Mockito.verify(examResultDao, Mockito.times(1)).aggregate(query2);
    }

    @Test
    public void getDataToCSVOfPendingResultsByAliquots_should_execute_queries() throws Exception {
        query1 =new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query2 =new LaboratoryProgressQueryBuilder().getPendingAliquotsCsvDataQuery(aliquotCodes,CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        when(aliquotDao.aggregate(query2)).thenReturn(result2);
        when(result2.first()).thenReturn(new Document("pendingAliquotsCsvData",  Arrays.asList()));
        laboratoryProgressDaoBean.getDataToCSVOfPendingResultsByAliquots(CENTER);
        Mockito.verify(examResultDao, Mockito.times(1)).aggregate(query1);
        Mockito.verify(aliquotDao, Mockito.times(1)).aggregate(query2);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataToCSVOfPendingResultsByAliquots_should_throw_DataNotFoundException_on_first_query_fail() throws Exception {
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        laboratoryProgressDaoBean.getDataToCSVOfPendingResultsByAliquots(CENTER);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataToCSVOfPendingResultsByAliquots_should_throw_DataNotFoundException_when_first_query_return_null() throws Exception {
        query1 =new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query2 =new LaboratoryProgressQueryBuilder().getPendingAliquotsCsvDataQuery(aliquotCodes,CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(null);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        laboratoryProgressDaoBean.getDataToCSVOfPendingResultsByAliquots(CENTER);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataToCSVOfPendingResultsByAliquots_should_throw_DataNotFoundException_when_second_query_return_null() throws Exception {
        query1 =new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery();
        ArrayList<String> aliquotCodes = new ArrayList<>();
        aliquotCodes.add("121212121");
        query2 =new LaboratoryProgressQueryBuilder().getPendingAliquotsCsvDataQuery(aliquotCodes,CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("aliquotCodes", aliquotCodes));
        when(aliquotDao.aggregate(query2)).thenReturn(result2);
        when(result2.first()).thenReturn(null);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        laboratoryProgressDaoBean.getDataToCSVOfPendingResultsByAliquots(CENTER);
    }

    @Test
    public void getDataOfStorageByAliquot_should_call_aliquotDaoAggregate_with_StorageByAliquotQuery() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(aliquotDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document());
        laboratoryProgressDaoBean.getDataOfStorageByAliquot(CENTER);
        verifyPrivate(laboratoryProgressDaoBean).invoke("aliquotDaoAggregate",query1);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataOfStorageByAliquot_should_throw_DataNotFoundException() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(CENTER);
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(aliquotDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(null);
        laboratoryProgressDaoBean.getDataOfStorageByAliquot(CENTER);
    }

    @Test
    public void getDataOfStorageByAliquot_should_return_LaboratoryProgressDTO_with_storageByAliquot() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(CENTER);
        String storageByAliquot = gsonBuilder.toJson(new Document("storageByAliquot", Arrays.asList()));
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(aliquotDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("storageByAliquot", Arrays.asList()));
        assertEquals(storageByAliquot,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataOfStorageByAliquot(CENTER)));
    }

    @Test
    public void getDataToCSVOfOrphansByExam_should_call_aliquotDaoAggregate_with_CSVOfPendingResultsQuery() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery();
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document());
        laboratoryProgressDaoBean.getDataToCSVOfOrphansByExam();
        verifyPrivate(laboratoryProgressDaoBean).invoke("examResultDaoAggregate",query1);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDataToCSVOfOrphansByExam_should_throw_DataNotFoundException() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery();
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(null);
        laboratoryProgressDaoBean.getDataToCSVOfOrphansByExam();
    }

    @Test
    public void getDataToCSVOfOrphansByExam_should_return_LaboratoryProgressDTO_with_pendingAliquotsCsvData() throws Exception {
        query1 = new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery();
        String storageByAliquot = gsonBuilder.toJson(new Document("orphanExamsCsvData", Arrays.asList()));
        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
        when(examResultDao.aggregate(query1)).thenReturn(result);
        when(result.first()).thenReturn(new Document("orphanExamsCsvData", Arrays.asList()));
        assertEquals(storageByAliquot,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataToCSVOfOrphansByExam()));
    }
}
