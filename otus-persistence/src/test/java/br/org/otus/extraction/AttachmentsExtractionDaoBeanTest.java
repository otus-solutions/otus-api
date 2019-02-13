package br.org.otus.extraction;

import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.attachments.AttachmentsReport;
import br.org.otus.extraction.builder.AttachmentsExtractionQueryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.persistence.ActivityDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.apache.commons.csv.CSVFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AttachmentsReport.class,AttachmentsExtractionDaoBean.class,CSVFormat.class})
public class AttachmentsExtractionDaoBeanTest {
    private static String CENTER = "RS";
    private static ArrayList<ObjectId> ID_LIST;
    private static Gson builder;

    private static final String acronym = "ANTC";
    private static final Integer version = 1;

    @InjectMocks
    private AttachmentsExtractionDaoBean attachmentsExtractionDaoBean;

    @Mock
    private ActivityDao activityDao;

    @Mock
    private FileStoreBucket fileStoreBucket;

    @Mock
    private AggregateIterable<Document> result;

    @Mock
    private AttachmentsReport.Attachment attachment;

    @Mock
    private List<AttachmentsReport.Attachment> attachmentsList = new ArrayList<>();

    @Mock
    private Document documentResult;


    @Before
    public void setUp() {
        builder = new GsonBuilder().create();
        ID_LIST = new ArrayList<>();
        ID_LIST.add(new ObjectId());
    }

    @Test
    public void fetchAttachmentsReport_should_call_execute_queries() throws DataNotFoundException {
        Mockito.when(fileStoreBucket.distinctIds()).thenReturn(new Document("ObjectIds",ID_LIST));
        ArrayList<Bson> query1 = new AttachmentsExtractionQueryBuilder().getAttachmentsStoredReportQuery(acronym, version, ID_LIST);
        ArrayList<Bson> query2 = new AttachmentsExtractionQueryBuilder().getAttachmentsRemovedReportQuery(acronym, version, ID_LIST);
        Mockito.when(activityDao.aggregate(query1)).thenReturn(result);
        Mockito.when(activityDao.aggregate(query2)).thenReturn(result);
        attachmentsList.add(attachment);
        Mockito.when(result.first()).thenReturn(documentResult);
        Mockito.when(documentResult.toJson()).thenReturn("{attachmentsList:[{questionId:teste}]}");
        attachmentsExtractionDaoBean.fetchAttachmentsReport(acronym,version);
        Mockito.verify(fileStoreBucket,Mockito.times(1)).distinctIds();
        Mockito.verify(activityDao,Mockito.times(1)).aggregate(query1);
        Mockito.verify(activityDao,Mockito.times(1)).aggregate(query2);
    }

    @Test(expected = DataNotFoundException.class)
    public void fetchAttachmentsReport_should_throw_DataNotFoundException() throws DataNotFoundException {
        Mockito.when(fileStoreBucket.distinctIds()).thenReturn(new Document("ObjectIds",ID_LIST));
        ArrayList<Bson> query1 = new AttachmentsExtractionQueryBuilder().getAttachmentsStoredReportQuery(acronym, version, ID_LIST);
        ArrayList<Bson> query2 = new AttachmentsExtractionQueryBuilder().getAttachmentsRemovedReportQuery(acronym, version, ID_LIST);
        Mockito.when(activityDao.aggregate(query1)).thenReturn(result);
        Mockito.when(activityDao.aggregate(query2)).thenReturn(result);
        attachmentsList.add(attachment);
        Mockito.when(result.first()).thenReturn(documentResult);
        Mockito.when(documentResult.toJson()).thenReturn("{attachmentsList:[]}");
        attachmentsExtractionDaoBean.fetchAttachmentsReport(acronym,version);
    }

    @Test(expected = DataNotFoundException.class)
    public void fetchAttachmentsReport_should_throw_DataNotFoundException_on_getFutureResult() throws DataNotFoundException {
        Mockito.when(fileStoreBucket.distinctIds()).thenReturn(new Document("ObjectIds",ID_LIST));
        Mockito.when(documentResult.toJson()).thenReturn("{attachmentsList:[]}");
        attachmentsExtractionDaoBean.fetchAttachmentsReport(acronym,version);
    }
}
