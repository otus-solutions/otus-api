package br.org.otus.response.info;

import static org.junit.Assert.*;

import org.junit.Test;

public class NotFoundTest {

	@Test
	public void method_build_should_return_instanceof_notFound() {
		assertTrue(NotFound.build() instanceof NotFound);
		
	}

}
