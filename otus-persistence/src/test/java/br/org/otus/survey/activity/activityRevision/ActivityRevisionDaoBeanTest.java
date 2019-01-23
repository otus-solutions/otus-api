package br.org.otus.survey.activity.activityRevision;

import br.org.otus.survey.activity.ActivityRevisionDaoBean;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(PowerMockRunner.class)
public class ActivityRevisionDaoBeanTest {
    private static final String ACTIVITY_REVISION_JSON = "{\"activityId\" : \"5c41c6b316da48006573a169\"," + "\"reviewDate\" : \"17/01/2019\"}";
    private static final ObjectId ACTIVITY_ID = new ObjectId("5c41c6b316da48006573a169");

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

    @Test
    public void method_get_should_persist_query_correct() {
        activitiesRevision = ActivityRevision.deserialize(ACTIVITY_REVISION_JSON);
        Document parsed = Document.parse(ActivityRevision.serialize(activitiesRevision));
        doNothing().when(collection).insertOne(parsed);
        activityRevisionDaoBean.persist(activitiesRevision);
        verify(collection,times(1)).insertOne(parsed);
    }

    @Test
    public void method_get_should_find_query_correct() throws DataNotFoundException {
        Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(documents);
        Mockito.when(documents.first()).thenReturn(document);

        activityRevisionDaoBean.find(ACTIVITY_ID);
        verify(collection,times(1)).find((Bson) Mockito.any());
    }
}
