package br.org.otus.extraction.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import br.org.otus.extraction.ExtractionFacade;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.user.api.UserFacade;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ExtractionResource.class)
public class ExtractionResourceTest {
	
	private static final String id = "ANTC";
	private static final Integer version = 1;
	
	@InjectMocks
	private ExtractionResource extractionResource;
	
	@Mock
	private UserFacade userFacade;
	@Mock
	private ExtractionFacade extractionFacade;
	@Mock
	private SecurityContext securityContext;
	
	byte[] test;

	@Before
	public void setUp() throws Exception {
		PowerMockito.when(extractionFacade.createActivityExtraction(id, version)).thenReturn(null);
	}


	@Test
	public void should_verify_method_createActivityExtraction_have_been_called() {
		extractionResource.extractActivities(id, version);
		Mockito.verify(extractionFacade).createActivityExtraction(id, version);
	}

}
