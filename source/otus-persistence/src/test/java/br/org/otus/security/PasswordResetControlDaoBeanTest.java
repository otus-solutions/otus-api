package br.org.otus.security;

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

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PasswordResetControlDaoBeanTest {

  private static final String EMAIL = "teste@teste.com.br";
  private static final String TOKEN = "5516516516516";
  private static String COLLECTION_NAME = "password_reset_control";

  @InjectMocks
  private PasswordResetControlDaoBean passwordResetControlDaoBean;

  @Mock
  private MongoDatabase mongoDatabase;

  @Mock
  private MongoCollection collection;

  @Mock
  private FindIterable<Document> documents;
  @Mock
  private Document document;

  @Before
  public void setUp() {
    Mockito.when(mongoDatabase.getCollection(COLLECTION_NAME, MongoCollection.class)).thenReturn(collection);
  }

  @Test
  public void should_call_register_token_into_database() {
    passwordResetControlDaoBean.registerToken(TOKEN, EMAIL);
    Mockito.verify(collection).insertOne(Mockito.anyObject());
  }

  @Test
  public void should_call_remove_into_database() {
    passwordResetControlDaoBean.removeRegister(TOKEN);
    Mockito.verify(collection).deleteOne(Mockito.anyObject());
  }

  @Test
  public void should_call_remove_all_entries_into_database() {
    passwordResetControlDaoBean.removeAllRegisters(TOKEN);
    Mockito.verify(collection).deleteMany(Mockito.anyObject());
  }

  @Test
  public void should_call_find_when_verify_existence_token_into_database() {
    Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(documents);
    Mockito.when(documents.first()).thenReturn(document);

    passwordResetControlDaoBean.tokenExists(TOKEN);
    Mockito.verify(collection).find((Bson) Mockito.any());
  }

  @Test
  public void should_call_find_when_verify_existence_of_register_into_database() {
    Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(documents);
    Mockito.when(documents.first()).thenReturn(document);

    passwordResetControlDaoBean.hasRegister(EMAIL);
    Mockito.verify(collection).find((Bson) Mockito.any());
  }

  @Test
  public void should_call_find_when_request_email_from_the_database() throws DataNotFoundException {
    Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(documents);
    Mockito.when(documents.first()).thenReturn(document);
    Mockito.when(document.get("email")).thenReturn(EMAIL);
    passwordResetControlDaoBean.getRequestEmail(TOKEN);
    Mockito.verify(collection).find((Bson) Mockito.any());
  }

  @Test(expected = DataNotFoundException.class)
  public void should_throw_DataNotFoundException_when_request_email_from_the_database() throws DataNotFoundException {
    Mockito.when(collection.find((Bson) Mockito.any())).thenReturn(documents);
    passwordResetControlDaoBean.getRequestEmail(TOKEN);
    Mockito.verify(collection).find((Bson) Mockito.any());
  }
}