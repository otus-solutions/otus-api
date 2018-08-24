package br.org.otus.user;

import br.org.otus.model.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoBeanTest {

  private static final String EMAIL = "teste@teste.com.br";
  private static final String TOKEN = "5516516516516";
  private static String COLLECTION_NAME = "user";

  @InjectMocks
  private UserDaoBean userDaoBean;

  @Mock
  private MongoDatabase mongoDatabase;

  @Mock
  private MongoCollection collection;

  @Mock
  private FindIterable<Document> documents;
  @Mock
  private Document document;

  @Mock
  private User user;

  @Before
  public void setUp() {
    Mockito.when(mongoDatabase.getCollection(COLLECTION_NAME, MongoCollection.class)).thenReturn(collection);
  }

  @Test
  public void should_call_insertOne() {
    userDaoBean.persist(user);
    Mockito.verify(collection).insertOne(Mockito.anyObject());
  }

  @Test(expected = DataNotFoundException.class)
  public void should_throw_DataNotFoundException() throws DataNotFoundException {
    Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(documents);
    Mockito.when(documents.first()).thenReturn(null);
    userDaoBean.fetchByEmail(EMAIL);
  }
}