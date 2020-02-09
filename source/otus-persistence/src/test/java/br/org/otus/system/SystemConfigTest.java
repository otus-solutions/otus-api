package br.org.otus.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.google.gson.GsonBuilder;

import br.org.otus.email.BasicEmailSender;

@RunWith(MockitoJUnitRunner.class)
public class SystemConfigTest {
  private static final String PROJECT_NAME = "Otus Local";
  private static final String DOMAIN_REST_URL = "http://api.domain.dev.ccem.ufrgs.br:8080";
  private static final String PROJECT_TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";
  private SystemConfig systemConfig;
  private String systemConfigJson;

  @Before
  public void setUp()
    throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    systemConfig = new SystemConfig();
    Whitebox.setInternalState(systemConfig, "projectName", PROJECT_NAME);
    Whitebox.setInternalState(systemConfig, "domainRestUrl", DOMAIN_REST_URL);
    systemConfig.setProjectToken(PROJECT_TOKEN);
    systemConfigJson = new GsonBuilder().create().toJson(systemConfig);
  }

  @Test
  public void method_deserialize_ahould_compare_internal_atributes_in_object_deserializable()
    throws NoSuchFieldException, SecurityException {
    assertEquals(systemConfig.getProjectToken(), SystemConfig.deserialize(systemConfigJson).getProjectToken());
    assertEquals(systemConfig.getProjectName(), SystemConfig.deserialize(systemConfigJson).getProjectName());
    assertEquals(systemConfig.getDomainRestUrl(), SystemConfig.deserialize(systemConfigJson).getDomainRestUrl());
  }

  @Test
  public void method_serialize_should_compare_JsonStrings_create_by_GsonBuilder() {
    assertEquals(systemConfigJson, SystemConfig.serialize(systemConfig));
  }

  @Test
  public void method_getEmailSender_should_return_instance_of_BasicEmailSender() {
    assertTrue(systemConfig.getEmailSender() instanceof BasicEmailSender);

  }

}
