package br.org.otus.user.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.mongodb.client.model.Filters.eq;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(PowerMockRunner.class)
public class UserActivityPendencyDaoBeanTest {

  public static final String OBJECT_TYPE = "objectType";
  public static String DEFAULT_OBJECT_TYPE = "User Activity Pendency";

  private static final String ACTIVITY_OID = "5c41c6b316da48006573a169";
  private static final String PENDENCY_ID = "5c41c6b316da48006573a169";
  private static final String PENDENCY_JSON = "{\n" +
    "\t\"objectType\": \"userActivityPendency\",\n" +
    "    \"creationDate\": \"2019-12-30T19:31:08.570Z\",\n" +
    "    \"dueDate\": \"2020-11-20T19:31:08.570Z\",\n" +
    "    \"requester\": \"flavia.avila@ufrgs.br\",\n" +
    "    \"receiver\": \"ativ_finalized4@otus.com\",\n" +
    "    \"activityInfo\": {\n" +
    "    \t\"id\": \"5a37fa6428f10d104371105c\",\n" +
    "    \t\"acronym\": \"ECGC\",\n" +
    "    \t\"recruitmentNumber\": 5000891\n" +
    "    \t\n" +
    "    }\n" +
    "}";

  @InjectMocks
  private UserActivityPendencyDaoBean userActivityPendencyDaoBean;
  @Mock
  private MongoCollection<Document> collection;
  @Mock
  private UserActivityPendency userActivityPendency;
  @Mock
  private FindIterable<Document> findInterable;
  @Mock
  private Document document; // ActivityRevisionDaoBeanTest
  private Document first = PowerMockito.spy(new Document(OBJECT_TYPE, DEFAULT_OBJECT_TYPE)); // ConfigurationDaoBeanTest

  private ObjectId objectId, activityOID;

  @Before
  public void setUp() throws Exception {
    objectId = new ObjectId(PENDENCY_ID);
    activityOID = new ObjectId(ACTIVITY_OID);
    userActivityPendency = UserActivityPendency.deserialize(PENDENCY_JSON);
  }

  @Test
  public void method_create_should_insert_pendency_correctly(){
    Document parsed = Document.parse(UserActivityPendency.serialize(userActivityPendency));
    doNothing().when(collection).insertOne(parsed);

    userActivityPendencyDaoBean.create(userActivityPendency);
    verify(collection,times(1)).insertOne(parsed);
  }

  @Test
  public void method_update_should_change_pendency_correctly() throws ValidationException, DataNotFoundException {
    String originalReceiver = userActivityPendency.getReceiver();
    userActivityPendency.setReceiver("another_receiver@otus.com");

    Bson idFilter = eq("_id", userActivityPendency.getId());
    Document setDueDateDoc = new Document("$set", new Document("dueDate", userActivityPendency.getDueDate()));
    Document setReceiver = new Document("$set", new Document("receiver", userActivityPendency.getReceiver()));

    doNothing().when(collection).updateOne(idFilter, setDueDateDoc);
    doNothing().when(collection).updateOne(idFilter, setReceiver);

    userActivityPendencyDaoBean.update(userActivityPendency.getId(), userActivityPendency);
    verify(collection,times(1)).updateOne(idFilter, setDueDateDoc);
    verify(collection,times(1)).updateOne(idFilter, setReceiver);

    userActivityPendency.setReceiver(originalReceiver);
  }

  @Test
  public void method_get_should_findByActivityInfo_query_correct() throws DataNotFoundException {
    Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(findInterable);
    Mockito.when(findInterable.first()).thenReturn(document);
    try {
      userActivityPendencyDaoBean.findByActivityInfo(activityOID);
    } catch (DataNotFoundException e) {
      assertTrue(e.getMessage().contains("No user activity pendency found for activityOID { " + activityOID  + " }."));
    }
    verify(collection,times(1)).find((Bson) Mockito.any());
  }

  @Test
  public void method_get_should_findAllPendencies_query_correct() throws DataNotFoundException {
    when(collection.find(Mockito.any(Document.class))).thenReturn(findInterable);
    when(findInterable.first()).thenReturn(document);
    try {
      userActivityPendencyDaoBean.findAllPendencies();
    } catch (DataNotFoundException | MemoryExcededException e) {
      assertTrue(e.getMessage().contains(""));
      System.out.println(e);//.
    }
    verify(collection,times(1)).find((Bson) Mockito.any());
  }


}
