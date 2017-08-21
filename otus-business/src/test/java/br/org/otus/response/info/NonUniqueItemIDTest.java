package br.org.otus.response.info;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NonUniqueItemIDTest {

	@Test
	public void method_Build_should_return_instanceOf_NonUniqueItemID() {
		assertTrue(NonUniqueItemID.build() instanceof NonUniqueItemID);
	}
}
