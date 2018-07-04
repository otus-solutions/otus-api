package br.org.otus.laboratory.participant;

import java.util.ArrayList;

//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;

@RunWith(PowerMockRunner.class)
public class ParticipantLaboratoryDaoBeanTest {
	
	private ParticipantLaboratoryDaoBean participantLaboratoryDaoBean;
	
	@Mock
	private WorkAliquotFiltersDTO workAliquotFiltersDTO;
	

	@Before
	public void setUp() throws Exception {
		
	}	

	@Test
	public void method_getAliquotsByPeriod_should_return_workAliquotList() {		
		
		//participantLaboratoryDaoBean.getAliquotsByPeriod(workAliquotFiltersDTO);
	}

	@Test
	public void testGetAliquot() {
		
	}

}
