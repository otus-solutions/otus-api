package br.org.otus.configuration;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

import model.ProjectConfiguration;

@RunWith(PowerMockRunner.class)
public class ConfigurationDaoBeanTest {
  private static String CONFIGURATION_JSON = "{\"objectType\": \"Project Configuration\", \"participantRegistration\": false\"}";
  private static final boolean PERMISSION = true;
  private static final Long RETURN_VALID = 1L;
  private static final Long RETURN_INVALID = 0L;
  public static final String OBJECT_TYPE = "objectType";
  public static String DEFAULT_OBJECT_TYPE = "Project Configuration";

  @InjectMocks
  private ConfigurationDaoBean configurationDaoBean = PowerMockito.spy(new ConfigurationDaoBean());
  @Mock
  private MongoCollection collection;
  @Mock
  private UpdateResult updateResult;
  @Mock
  private Document query;
  @Mock
  private FindIterable findleInterable;
  private Document first = PowerMockito.spy(new Document(OBJECT_TYPE, DEFAULT_OBJECT_TYPE));

  @Test
  public void enableParticipantRegistrationMethod_should_not_throw_an_exception() throws Exception {
    when(collection.updateOne(any(), any())).thenReturn(updateResult);
    when(updateResult.getMatchedCount()).thenReturn(RETURN_VALID);
    configurationDaoBean.enableParticipantRegistration(PERMISSION);
  }

  @Test(expected = DataNotFoundException.class)
  public void enableParticipantRegistrationMethod_should_throw_an_exception() throws DataNotFoundException {
    when(collection.updateOne(any(), any())).thenReturn(updateResult);
    when(updateResult.getMatchedCount()).thenReturn(RETURN_INVALID);
    configurationDaoBean.enableParticipantRegistration(PERMISSION);
  }

  @Test
  public void GetProjectConfigurationMethod_should_return_deserialize_projectConfiguration() throws Exception {
    when(collection.find(Mockito.any(Document.class))).thenReturn(findleInterable);
    when(findleInterable.first()).thenReturn(first);
    assertTrue(ProjectConfiguration.class.isInstance(configurationDaoBean.getProjectConfiguration()));
  }

  @Test(expected = DataNotFoundException.class)
  public void GetProjectConfigurationMethod_should_throw_DataNotFoundException() throws DataNotFoundException {
    when(collection.find(Mockito.any(Document.class))).thenReturn(findleInterable);
    configurationDaoBean.getProjectConfiguration();
  }
}
