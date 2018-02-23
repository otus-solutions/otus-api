package br.org.otus.laboratory.participant.aliquot;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;

//TODO: by virtue of the aliquotCollectionData class, this test was implemented to protect the attributes,
//at another time implement the other methods

@RunWith(PowerMockRunner.class)
public class AliquotTest {
	public static final String OBJECT_TYPE = "Alicot";
	public static final String CODE =  "343005098";
	public static final String NAME = "FASTING_HORMONE";		
	private Aliquot aliquot;
	private AliquotContainer aliquotContainer;
	private AliquotRole aliquotRole;	
	@Mock
	private AliquotCollectionData aliquotCollectionData;	

	@Before
	public void setUp() {
		aliquot = new Aliquot(OBJECT_TYPE, CODE, NAME, aliquotContainer, aliquotRole, aliquotCollectionData);
	}	

	@Test
	public void method_should_check_aliquot_constructor_integrity_() {
		assertTrue(Aliquot.class.isInstance(aliquot));
		
	}

}
