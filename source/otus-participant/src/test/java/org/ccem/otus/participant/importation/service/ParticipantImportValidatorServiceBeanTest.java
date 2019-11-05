package org.ccem.otus.participant.importation.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.importation.model.ParticipantImport;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ParticipantImportValidatorServiceBean.class)
public class ParticipantImportValidatorServiceBeanTest {
	private static final String FIELD_CEENTER_ACRONYM = "RS";
	private static final Boolean FIELD_CENTER_CONTAINS = true;
	private static final Boolean AVALIABLE_FIELD_CENTER_FULL = false;
	@InjectMocks
	private ParticipantImportValidatorServiceBean participantImportValidatorServiceBean;
	@Mock
	private FieldCenterDao fieldCenterDao;
	@Mock
	private ParticipantDao participantDao;
	@Mock
	private ParticipantImport participantToImport;
	private List<String> availableFieldCenterAcronyms = Mockito.spy(new ArrayList());
	private Set<ParticipantImport> participantToImports;

	@Before
	public void setUp() throws Exception {
		participantToImports = new HashSet<>();
		participantToImports.add(participantToImport);
	}

	@Test(expected = ValidationException.class)
	public void method_isImportable_should_throws_ValidationException_if_availableFieldCenterAcronyms_isEmpty()
			throws org.ccem.otus.exceptions.webservice.validation.ValidationException {
		participantImportValidatorServiceBean.isImportable(participantToImports);
		Mockito.verify(availableFieldCenterAcronyms).isEmpty();
	}

	// TODO Class that is being tested needs refactoring in the SetUp to
	// complete the next scenarios (We need to Instantiate availableFieldCenterAcronyms by
	// method private GetAvailableFieldCenterAcronyms)

}
