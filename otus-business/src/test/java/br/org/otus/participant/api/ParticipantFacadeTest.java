package br.org.otus.participant.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.exception.ResponseInfo;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ParticipantFacade.class)
public class ParticipantFacadeTest {
	private static long RN = 1063154;
	@InjectMocks
	private ParticipantFacade participantFacade;
	@Mock
	private ParticipantService participantService;
	@Mock
	private Participant participant;
	@Mock
	private FieldCenter fieldCenter;
	@Mock
	private java.util.List<Participant> partipantList;
	@Mock
	private DataNotFoundException e;
	
	@Test
	public void method_getByRecruitmentNumber_should_return_participant() throws DataNotFoundException {
		when(participantService.getByRecruitmentNumber(RN)).thenReturn(participant);
		assertTrue(participantFacade.getByRecruitmentNumber(RN) instanceof Participant);
	}

	// TODO teste do HttpResponseException causa nullpointer por causa da
	// chamada do metodo estático ResponseBuild no paramentro
	// talvez whenNew HttpResponseException withAnyArguments possa validadar o
	// teste, mas teria que usar powerMockito e perde cobertura.	
	

	@Test
	public void method_list_should_return_instanceOf_ParticipantList() {
		when(participantService.list(fieldCenter)).thenReturn(partipantList);
		assertTrue(participantFacade.list(fieldCenter) instanceof java.util.List);
	}

}
