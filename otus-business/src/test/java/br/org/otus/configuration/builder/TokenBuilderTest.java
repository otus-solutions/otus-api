package br.org.otus.configuration.builder;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TokenBuilder.class)
public class TokenBuilderTest {
	private static String UUID_STRING = "45b07151-5824-4001-acb0-23981fad36aa";	

	@Test
	public void testBuild() {
		mockStatic(UUID.class);
		Mockito.when(UUID.randomUUID().toString()).thenReturn(UUID_STRING);		
		assertEquals(UUID_STRING, TokenBuilder.build());
	}
}
