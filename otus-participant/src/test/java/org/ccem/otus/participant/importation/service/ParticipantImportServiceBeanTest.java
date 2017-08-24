package org.ccem.otus.participant.importation.service;

import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.invokeMethod;

import java.util.HashSet;
import java.util.Set;

import org.ccem.otus.participant.builder.ParticipantBuilder;
import org.ccem.otus.participant.importation.model.ParticipantImport;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.persistence.FieldCenterDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ParticipantImportServiceBean.class)
public class ParticipantImportServiceBeanTest {
	@InjectMocks
	ParticipantImportServiceBean participantImportServiceBean = PowerMockito.spy(new ParticipantImportServiceBean());
	@Mock
	private ParticipantImportValidatorService participantImportValidatorService;
	@Mock
	private ParticipantService participantService;
	@Mock
	private FieldCenterDao fieldCenterDao;
	@Mock
	private ParticipantBuilder participantBuilder;
	@Mock
	private ParticipantImport participantToImport;
	@Mock
	private Participant participant;
	Set<ParticipantImport> participantImports;

	@Before
	public void setUp() {
		participantImports = new HashSet<>();
		participantImports.add(participantToImport);
	}

	@Test
	public void method_importation_should_evocate_performImportationMethod() throws Exception {
		doNothing().when(participantImportServiceBean, "performImportation", participantImports);
		participantImportServiceBean.importation(participantImports);
		verifyPrivate(participantImportServiceBean).invoke("performImportation", participantImports);
	}

	@Test
	public void method_performImportation_should_evocate_internal_methods() throws Exception {
		whenNew(ParticipantBuilder.class).withAnyArguments().thenReturn(participantBuilder);
		when(participantBuilder.buildFromPartipantToImport(participantToImport)).thenReturn(participant);
		invokeMethod(participantImportServiceBean, "performImportation", participantImports);
		verifyPrivate(participantImportValidatorService).invoke("isImportable", participantImports);
		verifyNew(ParticipantBuilder.class).withArguments(Matchers.any());
		verifyPrivate(participantService).invoke("create", participant);
	}

}
