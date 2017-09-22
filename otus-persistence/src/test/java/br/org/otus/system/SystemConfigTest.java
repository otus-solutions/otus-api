package br.org.otus.system;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.google.gson.GsonBuilder;

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
	public void method_deserialize() throws NoSuchFieldException, SecurityException {
		assertEquals(systemConfig.getProjectToken(), SystemConfig.deserialize(systemConfigJson).getProjectToken());
		assertEquals(systemConfig.getProjectName(), SystemConfig.deserialize(systemConfigJson).getProjectName());
		assertEquals(systemConfig.getDomainRestUrl(), SystemConfig.deserialize(systemConfigJson).getDomainRestUrl());
	}

	@Test
	public void method_serialize() {
		assertEquals(systemConfigJson, SystemConfig.serialize(systemConfig));
	}

}
