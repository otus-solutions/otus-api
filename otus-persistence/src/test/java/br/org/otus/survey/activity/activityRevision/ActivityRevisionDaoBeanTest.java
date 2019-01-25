package br.org.otus.survey.activity.activityRevision;

import br.org.otus.survey.activity.ActivityRevisionDaoBean;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;


import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class ActivityRevisionDaoBeanTest {
    private static final String ACTIVITY_REVISION_JSON = "{\"activityID\" : \"5c41c6b316da48006573a169\"," + "\"reviewDate\" : \"17/01/2019\"}";
    private static final String ACTIVITY_ID = "5c41c6b316da48006573a169";

    @InjectMocks
    private ActivityRevisionDaoBean activityRevisionDaoBean;
    @Mock
    private MongoCollection<Document> collection;
    @Mock
    private ActivityRevision activitiesRevision;
    @Mock
    private FindIterable<Document> documents;
    @Mock
    private Document document;
    private ObjectId objectId;

    @Before
    public void setUp() {
        objectId = new ObjectId(ACTIVITY_ID);
        activitiesRevision = ActivityRevision.deserialize(ACTIVITY_REVISION_JSON);
    }

    @Test
    public void method_get_should_persist_query_correct() {
        Document parsed = Document.parse(ActivityRevision.serialize(activitiesRevision));
        doNothing().when(collection).insertOne(parsed);

        activityRevisionDaoBean.persist(activitiesRevision);
        verify(collection,times(1)).insertOne(parsed);
    }

    @Test
    public void method_get_should_findByActivityID_query_correct() throws DataNotFoundException {
        Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(documents);
        Mockito.when(documents.first()).thenReturn(document);
        try {
            activityRevisionDaoBean.findByActivityID(objectId);
        } catch (DataNotFoundException e) {
            assertTrue(e.getMessage().contains("activityID {" + objectId + "} not found."));
        }
        verify(collection,times(1)).find((Bson) Mockito.any());
    }

    @Test(expected = DataNotFoundException.class)
    public void should_throw_DataNotFoundException() throws DataNotFoundException {
        Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(documents);
        Mockito.when(documents.first()).thenReturn(null);

        activityRevisionDaoBean.findByActivityID(objectId);
    }
}
