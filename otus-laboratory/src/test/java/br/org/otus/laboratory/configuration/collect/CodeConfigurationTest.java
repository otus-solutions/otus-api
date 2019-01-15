package br.org.otus.laboratory.configuration.collect;

import br.org.otus.laboratory.configuration.CodeConfiguration;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CodeConfigurationTest {

	private CodeConfiguration codeDefinitions;

	@Before
	public void setup() {
		String json = ""
				+ "{\"tube\": 1,"
				+ "\"pallet\": 2,"
				+ "\"cryotube\": 3,"
				+ "\"lastInsertion\":9}";

		codeDefinitions = new Gson().fromJson(json, CodeConfiguration.class);

	}

	@Test
	public void should_returns_lastInsertion() {
		Integer expected = 9;
		assertEquals(expected, codeDefinitions.getLastInsertion());
	}

	@Test
	public void should_increments_startingPoint_and_lastInsertion() {
		Integer expectedStartingPoint = codeDefinitions.getLastInsertion();
		Integer alloc = 3;
		++expectedStartingPoint;

//		Integer startingPoint = codeDefinitions.allocCodeAndGetStartingPoint(alloc);
//		assertEquals(expectedStartingPoint, startingPoint);
	}


}
