//package br.org.otus.monitoring.laboratory;
//
//import br.org.otus.examUploader.persistence.ExamResultDao;
//import br.org.otus.laboratory.participant.aliquot.Aliquot;
//import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
//import br.org.otus.monitoring.builder.LaboratoryProgressQueryBuilder;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.mongodb.client.AggregateIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import org.bson.Document;
//import org.bson.conversions.Bson;
//import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
//import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Matchers;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.powermock.reflect.Whitebox;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.powermock.api.mockito.PowerMockito.*;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ LaboratoryProgressQueryBuilder.class, LaboratoryProgressDao.class})
//public class LaboratoryProgressDaoBeanTest {
//    private static Gson gsonBuilder = new GsonBuilder().create();
//    private static String CENTER = "RS";
//
//    @InjectMocks
//    private LaboratoryProgressDaoBean laboratoryProgressDaoBean;
//
//    @Mock
//    private LaboratoryProgressQueryBuilder laboratoryProgressQueryBuilder;
//
//    @Mock
//    private AliquotDao aliquotDao;
//
//    @Mock
//    private ExamResultDao examResultDao;
//
//    @Mock
//    private AggregateIterable<Document> result;
//
//    @Mock
//    List<Bson> query;
//
//    @Before
//    public void setUp() throws Exception {
//
//        laboratoryProgressQueryBuilder = PowerMockito.spy(new LaboratoryProgressQueryBuilder());
//        PowerMockito
//                .whenNew(LaboratoryProgressQueryBuilder.class)
//                .withNoArguments()
//                .thenReturn(laboratoryProgressQueryBuilder);
//
//
//    }
//
//    @Test
//    public void getDataOrphanByExams_should_call_examResultDaoAggregate_with_OrphansQuery() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getOrphansQuery();
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(examResultDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document());
//        laboratoryProgressDaoBean.getDataOrphanByExams();
//        verifyPrivate(laboratoryProgressDaoBean).invoke("examResultDaoAggregate",query);
//    }
//
//    @Test(expected = DataNotFoundException.class)
//    public void getDataOrphanByExams_should_throw_DataNotFoundException() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getOrphansQuery();
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(examResultDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(null);
//        laboratoryProgressDaoBean.getDataOrphanByExams();
//    }
//
//    @Test
//    public void getDataOrphanByExams_should_return_LaboratoryProgressDTO_with_orphanExamsProgress() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getOrphansQuery();
//        String orphanExamsProgress = gsonBuilder.toJson(new Document("orphanExamsProgress", Arrays.asList()));
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(examResultDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document("orphanExamsProgress", Arrays.asList()));
//        assertEquals(orphanExamsProgress,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataOrphanByExams()));
//    }
//
//    @Test
//    public void getDataQuantitativeByTypeOfAliquots_should_call_aliquotDaoAggregate_with_QuantitativeQuery() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getQuantitativeQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document());
//        laboratoryProgressDaoBean.getDataQuantitativeByTypeOfAliquots(CENTER);
//        verifyPrivate(laboratoryProgressDaoBean).invoke("aliquotDaoAggregate",query);
//    }
//
//    @Test(expected = DataNotFoundException.class)
//    public void getDataQuantitativeByTypeOfAliquots_should_throw_DataNotFoundException() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getQuantitativeQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(null);
//        laboratoryProgressDaoBean.getDataQuantitativeByTypeOfAliquots(CENTER);
//    }
//
//    @Test
//    public void getDataQuantitativeByTypeOfAliquots_should_return_LaboratoryProgressDTO_with_quantitativeByTypeOfAliquots() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getQuantitativeQuery(CENTER);
//        String orphanExamsProgress = gsonBuilder.toJson(new Document("quantitativeByTypeOfAliquots", Arrays.asList()));
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document("quantitativeByTypeOfAliquots", Arrays.asList()));
//        assertEquals(orphanExamsProgress,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataQuantitativeByTypeOfAliquots(CENTER)));
//    }
//
//    @Test
//    public void getDataOfPendingResultsByAliquot_should_call_aliquotDaoAggregate_with_PendingResultsQuery() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getPendingResultsQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document());
//        laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER);
//        verifyPrivate(laboratoryProgressDaoBean).invoke("aliquotDaoAggregate",query);
//    }
//
//    @Test(expected = DataNotFoundException.class)
//    public void getDataOfPendingResultsByAliquot_should_throw_DataNotFoundException() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getPendingResultsQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(null);
//        laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER);
//    }
//
//    @Test
//    public void getDataOfPendingResultsByAliquot_should_return_LaboratoryProgressDTO_with_pendingResultsByAliquot() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getPendingResultsQuery(CENTER);
//        String orphanExamsProgress = gsonBuilder.toJson(new Document("pendingResultsByAliquot", Arrays.asList()));
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document("pendingResultsByAliquot", Arrays.asList()));
//        assertEquals(orphanExamsProgress,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataOfPendingResultsByAliquot(CENTER)));
//    }
//
//    @Test
//    public void getDataOfStorageByAliquot_should_call_aliquotDaoAggregate_with_StorageByAliquotQuery() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document());
//        laboratoryProgressDaoBean.getDataOfStorageByAliquot(CENTER);
//        verifyPrivate(laboratoryProgressDaoBean).invoke("aliquotDaoAggregate",query);
//    }
//
//    @Test(expected = DataNotFoundException.class)
//    public void getDataOfStorageByAliquot_should_throw_DataNotFoundException() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(null);
//        laboratoryProgressDaoBean.getDataOfStorageByAliquot(CENTER);
//    }
//
//    @Test
//    public void getDataOfStorageByAliquot_should_return_LaboratoryProgressDTO_with_storageByAliquot() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(CENTER);
//        String storageByAliquot = gsonBuilder.toJson(new Document("storageByAliquot", Arrays.asList()));
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document("storageByAliquot", Arrays.asList()));
//        assertEquals(storageByAliquot,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataOfStorageByAliquot(CENTER)));
//    }
//
//    @Test
//    public void getDataByExam_should_call_aliquotDaoAggregate_with_DataByExamQuery() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getDataByExamQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document());
//        laboratoryProgressDaoBean.getDataByExam(CENTER);
//        verifyPrivate(laboratoryProgressDaoBean).invoke("aliquotDaoAggregate",query);
//    }
//
//    @Test(expected = DataNotFoundException.class)
//    public void getDataByExam_should_throw_DataNotFoundException() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getDataByExamQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(null);
//        laboratoryProgressDaoBean.getDataByExam(CENTER);
//    }
//
//    @Test
//    public void getDataByExam_should_return_LaboratoryProgressDTO_with_examsQuantitative() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getDataByExamQuery(CENTER);
//        String storageByAliquot = gsonBuilder.toJson(new Document("examsQuantitative", Arrays.asList()));
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document("examsQuantitative", Arrays.asList()));
//        assertEquals(storageByAliquot,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataByExam(CENTER)));
//    }
//
//    @Test
//    public void getDataToCSVOfPendingResultsByAliquots_should_call_aliquotDaoAggregate_with_CSVOfPendingResultsQuery() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getCSVOfPendingResultsQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document());
//        laboratoryProgressDaoBean.getDataToCSVOfPendingResultsByAliquots(CENTER);
//        verifyPrivate(laboratoryProgressDaoBean).invoke("aliquotDaoAggregate",query);
//    }
//
//    @Test(expected = DataNotFoundException.class)
//    public void getDataToCSVOfPendingResultsByAliquots_should_throw_DataNotFoundException() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getCSVOfPendingResultsQuery(CENTER);
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(null);
//        laboratoryProgressDaoBean.getDataToCSVOfPendingResultsByAliquots(CENTER);
//    }
//
//    @Test
//    public void getDataToCSVOfPendingResultsByAliquots_should_return_LaboratoryProgressDTO_with_pendingAliquotsCsvData() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getCSVOfPendingResultsQuery(CENTER);
//        String storageByAliquot = gsonBuilder.toJson(new Document("pendingAliquotsCsvData", Arrays.asList()));
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(aliquotDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document("pendingAliquotsCsvData", Arrays.asList()));
//        assertEquals(storageByAliquot,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataToCSVOfPendingResultsByAliquots(CENTER)));
//    }
//
//    @Test
//    public void getDataToCSVOfOrphansByExam_should_call_aliquotDaoAggregate_with_CSVOfPendingResultsQuery() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery();
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(examResultDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document());
//        laboratoryProgressDaoBean.getDataToCSVOfOrphansByExam();
//        verifyPrivate(laboratoryProgressDaoBean).invoke("examResultDaoAggregate",query);
//    }
//
//    @Test(expected = DataNotFoundException.class)
//    public void getDataToCSVOfOrphansByExam_should_throw_DataNotFoundException() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery();
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(examResultDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(null);
//        laboratoryProgressDaoBean.getDataToCSVOfOrphansByExam();
//    }
//
//    @Test
//    public void getDataToCSVOfOrphansByExam_should_return_LaboratoryProgressDTO_with_pendingAliquotsCsvData() throws Exception {
//        query = new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery();
//        String storageByAliquot = gsonBuilder.toJson(new Document("orphanExamsCsvData", Arrays.asList()));
//        whenNew(LaboratoryProgressQueryBuilder.class).withNoArguments().thenReturn(laboratoryProgressQueryBuilder);
//        when(examResultDao.aggregate(query)).thenReturn(result);
//        when(result.first()).thenReturn(new Document("orphanExamsCsvData", Arrays.asList()));
//        assertEquals(storageByAliquot,gsonBuilder.toJson(laboratoryProgressDaoBean.getDataToCSVOfOrphansByExam()));
//    }
//}
