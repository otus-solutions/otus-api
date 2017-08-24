package org.ccem.otus.participant.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;

import java.util.List;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.importation.model.ParticipantImport;
import org.ccem.otus.participant.model.Participant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ParticipantBuilder.class)
public class ParticipantBuilderTest {
	private static final long RN = 1063154;
	private static final String NAME = "Luis Alberto";
	private static final String SEX = "M";
	private static final String BIRTHDATE = "1954-09-20";
	private static final String CENTER = "SP";
	@InjectMocks
	private ParticipantBuilder participantBuilder;
	@Mock
	private List<FieldCenter> availablefieldCenters;
	@Mock
	ParticipantImport participantImport;
	@Mock
	private FieldCenter fieldCenter;
	Participant participant;

	@Test
	public void method_buildFromPartipantToImport_should_return_participant() throws Exception {
		participantBuilder = spy(new ParticipantBuilder(availablefieldCenters));
		when(participantImport.getRn()).thenReturn(RN);
		when(participantImport.getName()).thenReturn(NAME);
		when(participantImport.getSex()).thenReturn(SEX);
		when(participantImport.getBirthdate()).thenReturn(BIRTHDATE);
		when(participantImport.getCenter()).thenReturn(CENTER);
		doReturn(fieldCenter).when(participantBuilder, "getFieldCenterByInitials", CENTER);
		assertEquals(NAME, participantBuilder.buildFromPartipantToImport(participantImport).getName());
		assertTrue(participantBuilder.buildFromPartipantToImport(participantImport) instanceof Participant);
	}

}
