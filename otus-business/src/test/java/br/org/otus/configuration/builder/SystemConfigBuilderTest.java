package br.org.otus.configuration.builder;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.model.User;
import br.org.otus.system.SystemConfig;
import br.org.tutty.Equalizer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SystemConfigBuilder.class, Equalizer.class, User.class })
public class SystemConfigBuilderTest {
	private static final String PROJECT_TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";
	private SystemConfigBuilder systemConfigBuilder;
	@Mock
	private OtusInitializationConfigDto otusInitializationConfigDto;
	@Mock
	private User user;
	@Mock
	private SystemConfig systemConfig;

	@Before
	public void setUp() {
		systemConfigBuilder = spy(new SystemConfigBuilder());
		mockStatic(Equalizer.class);
	}

	@Test
	public void method_buildInitialUser_should_verify_internals_calls_and_return_instance_of_the_User()
			throws Exception {
		whenNew(User.class).withNoArguments().thenReturn(user);
		doNothing().when(Equalizer.class, "equalize", Mockito.anyObject(), Mockito.anyObject());
		assertTrue(systemConfigBuilder.buildInitialUser(otusInitializationConfigDto) instanceof User);
		verifyNew(User.class).withNoArguments();
		verifyStatic(Mockito.times(1));
		Equalizer.equalize(otusInitializationConfigDto.getUser(), user);
		verify(user).becomesAdm();

	}

	@Test
	public void method_builSystemConfig_should_internal_calls_and_return_instance_of_the_SystemConfig()
			throws Exception {
		whenNew(SystemConfig.class).withNoArguments().thenReturn(systemConfig);
		doNothing().when(Equalizer.class, "equalize", Mockito.anyObject(), Mockito.anyObject());
		assertTrue(systemConfigBuilder.builSystemConfig(otusInitializationConfigDto,
				PROJECT_TOKEN) instanceof SystemConfig);
		verifyNew(SystemConfig.class).withNoArguments();
		verifyStatic(Mockito.times(2));
		Equalizer.equalize(otusInitializationConfigDto.getProject(), systemConfig);
		verifyStatic(Mockito.times(1));
		Equalizer.equalize(otusInitializationConfigDto.getEmailSender(), systemConfig.getEmailSender());
		verify(systemConfig).setProjectToken(PROJECT_TOKEN);
	}

}
