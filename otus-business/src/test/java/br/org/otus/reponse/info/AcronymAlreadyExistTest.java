package br.org.otus.reponse.info;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.info.AcronymAlreadyExist;


public class AcronymAlreadyExistTest {
	
	@Test
	public void method_should_verify_AcronymAlreadyExist_instance() {	
		assertTrue(AcronymAlreadyExist.class.isInstance(AcronymAlreadyExist.build()));		
	}	
}


