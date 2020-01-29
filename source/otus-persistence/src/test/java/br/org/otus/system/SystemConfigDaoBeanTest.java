package br.org.otus.system;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.persistence.NoResultException;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import br.org.otus.email.BasicEmailSender;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SystemConfig.class})
public class SystemConfigDaoBeanTest {
  private static final String SYSTEM_CONFIG_JSON = "{\"projectName\":\"Otus Local\",\"domainRestUrl\":\"http://api.domain.dev.ccem.ufrgs.br:8080\",\"projectToken\":\"347bcf7e-dcb2-4768-82eb-ee95d893f4c0\",\"basicEmailSender\":{}}";

  @InjectMocks
  private SystemConfigDaoBean systemConfigDaoBean = PowerMockito.spy(new SystemConfigDaoBean());
  private SystemConfig systemConfig = PowerMockito.spy(new SystemConfig());
  @Mock
  private MongoCollection collection;
  @Mock
  private FindIterable collectionFind;
  @Mock
  private Document result;
  @Mock
  private BasicEmailSender basicEmailSender;
  private Document resultNull;

  @Before
  public void setUp() {
    PowerMockito.mockStatic(SystemConfig.class);
    when(collection.find()).thenReturn(collectionFind);
  }

  @Test
  public void method_persistSystemConfig_must_mock_the_behavior_of_the_persistMethod_in_parentClass()
    throws Exception {
    when(SystemConfig.class, "serialize", systemConfig).thenReturn(SYSTEM_CONFIG_JSON);
    doNothing().when(systemConfigDaoBean, "persist", Mockito.any(String.class));
    systemConfigDaoBean.persist(systemConfig);
  }

  @Test
  public void method_isReady_should_return_true_case_result_of_collectionFistFind_isNotNull() {
    when(collectionFind.first()).thenReturn(result);
    assertTrue(systemConfigDaoBean.isReady());
  }

  @Test
  public void method_fetchSystemConfig_should_return_systemConfig_case_result_of_collectionFistFind_isNotNull() {
    when(collectionFind.first()).thenReturn(result);
    when(result.toJson()).thenReturn(SYSTEM_CONFIG_JSON);
    when(SystemConfig.deserialize(SYSTEM_CONFIG_JSON)).thenReturn(systemConfig);
    assertTrue(systemConfigDaoBean.fetchSystemConfig() instanceof SystemConfig);
  }

  @Test(expected = NoResultException.class)
  public void method_fetchSystemConfig_should_NoResultException_case_result_of_collectionFistFind_isNull() {
    when(collectionFind.first()).thenReturn(resultNull);
    systemConfigDaoBean.fetchSystemConfig();
  }

  @Test
  public void method_findEmailSender_should_return_BasicEmailSender() {
    when(collectionFind.first()).thenReturn(result);
    when(systemConfigDaoBean.fetchSystemConfig()).thenReturn(systemConfig);
    when(systemConfig.getEmailSender()).thenReturn(basicEmailSender);
    assertTrue(systemConfigDaoBean.findEmailSender() instanceof BasicEmailSender);

  }

}
