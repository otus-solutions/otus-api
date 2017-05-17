package br.org.otus.laboratory.collect.tube;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import br.org.otus.laboratory.LaboratoryConfigurationService;
import br.org.otus.laboratory.collect.tube.generator.DefaultTubeGenerator;
import br.org.otus.laboratory.collect.tube.generator.TubeGenerator;
import br.org.otus.laboratory.collect.tube.generator.TubeSeed;

public class TubeServiceBeanTest {

	// TODO Ir implementando este teste comforme as classes menores forem
	// terminando

	@InjectMocks
	private LaboratoryConfigurationService laboratoryConfigurationService;
	@Mock
	private TubeGenerator defaultTubeGenerator;

	@InjectMocks
	private TubeSeed tubeSeed;

	@Before
	public void setUp() {

		PowerMockito.when(defaultTubeGenerator.generateTubes(tubeSeed)).thenReturn((List<Tube>) new Tube("GEL", "FASTING", "351000342", "DEFAULT"));				

	}

	@Test
	public void method_should_generateTubes() {
		

	}

}
