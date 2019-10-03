package br.org.otus.response.info;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.org.otus.response.exception.ResponseInfo;

public class CommunicationFailTest {

	@Test
	public void method_Build_() {
		assertTrue(CommunicationFail.build() instanceof ResponseInfo);
	}
}
