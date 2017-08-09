package br.org.otus.response.info;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.ResponseInfo;


@RunWith(PowerMockRunner.class)
public class CommunicationFailTest {
	
	@Test
	public void method_Build_() {		
		assertTrue(CommunicationFail.build() instanceof ResponseInfo);		
	}	
	

}
