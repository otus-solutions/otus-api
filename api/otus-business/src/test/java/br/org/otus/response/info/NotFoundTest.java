package br.org.otus.response.info;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NotFoundTest {

	@Test
	public void method_build_should_return_instanceOf_NotFound() {
		assertTrue(NotFound.build() instanceof NotFound);
		
		
	}

}
