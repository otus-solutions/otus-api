package br.org.otus.survey.activity.activityReview;

import br.org.otus.survey.activity.ActivityReviewDaoBean;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class ActivityReviewDaoBeanTest {
    private static final String JSON = "{\"activityId\" : \"5c41c6b316da48006573a169\"," + "\"reviewDate\" : \"17/01/2019\"}";

    @InjectMocks
    private ActivityReviewDaoBean activityReviewDaoBean = PowerMockito.spy(new ActivityReviewDaoBean());
    private ActivityReview activityReview = PowerMockito.spy(new ActivityReview());

    @Mock
    private MongoCollection<Document> collection;
    @Mock
    private ArrayList<ActivityReview> activitiesReview;

    private MongoCursor cursor = PowerMockito.mock(MongoCursor.class);

    @Test
    public void method_get_should_persist_query_correct() throws Exception {
        when(ActivityReview.class, "serialize", activityReview).thenReturn(JSON);
        doNothing().when(activityReviewDaoBean, "persist", Mockito.any(ObjectId.class, new ObjectIdAdapter()));
        activityReviewDaoBean.persist(activityReview);

    }
}
