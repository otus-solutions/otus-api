package br.org.otus.laboratory.participant.aliquot;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AliquotCollectionDataTest {
	
		
		public static final String METADATA = "{}";
		public static final String OPERATOR = "test";
		public static final LocalDateTime TIME = LocalDateTime.of(2017,02,23,13,26);	
		public static final LocalDateTime PROCESSING = LocalDateTime.of(2017,02,23,13,26);		
		
		private AliquotCollectionData aliquotCollectionData;		

		@Test
		public void method_should_check_aliquotCollectionData_constructor_integrity() {	
			aliquotCollectionData = new AliquotCollectionData(METADATA, OPERATOR, TIME, PROCESSING);		
			assertTrue(AliquotCollectionData.class.isInstance(aliquotCollectionData));		
		}
}




