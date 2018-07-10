package br.org.otus.laboratory.project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.json.JSONException;

//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.api.TransportationLotFacade;
import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorkAliquotFiltersDTO.class, AuthorizationHeaderReader.class })
public class TransportationResourceTest {

	private static final String EMAIL = "otus@otus.com";
	private String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9";
	private static final String ALIQUOT_TEXT = "{recruitmentNumber: 1090810, fieldCenter: {acronym:'SP'}, code: 363123470 ,role:'STORAGE', objectType:'LotALiquot'}";

	@InjectMocks
	private TransportationResource transportationResource;
	@Mock
	private TransportationLotFacade transportationLotFacade;
	@Mock
	private SecurityContext securityContext;
	@Mock
	private HttpServletRequest request;
	@Mock
	private SessionIdentifier sessionIdentifier;
	@Mock
	private AuthenticationData authenticationData;

	private List<WorkAliquot> workAliquots;
	private String filterWorkAliquotJson;
	private GsonBuilder builder;
	private WorkAliquot workAliquot;

	@Before
	public void setUp() throws Exception {

		when(request.getHeader(anyString())).thenReturn(TOKEN);
		mockStatic(AuthorizationHeaderReader.class);
		when(AuthorizationHeaderReader.readToken(TOKEN)).thenReturn(TOKEN);
		when(securityContext.getSession(TOKEN)).thenReturn(sessionIdentifier);
		when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
		when(authenticationData.getUserEmail()).thenReturn(EMAIL);
		
		workAliquot = WorkAliquot.deserialize(ALIQUOT_TEXT);
		workAliquots = Arrays.asList(workAliquot);
		mockStatic(WorkAliquotFiltersDTO.class);
		WorkAliquotFiltersDTO workAliquotFiltersDTO = WorkAliquotFiltersDTO.deserialize(filterWorkAliquotJson);
		when(WorkAliquotFiltersDTO.deserialize(filterWorkAliquotJson)).thenReturn(workAliquotFiltersDTO);
		builder = WorkAliquot.getGsonBuilder();
	}

	@Test
	public void method_getAliquotsByPeriod_should_return_response_with_stringArrayWorkAliquots() throws Exception {
		when(transportationLotFacade.getAliquotsByPeriod(Mockito.any())).thenReturn(workAliquots);
		assertEquals(transportationResource.getAliquotsByPeriod(request, filterWorkAliquotJson),
				new Response().buildSuccess(builder.create().toJson(workAliquots)).toJson());
	}

	@Test
	public void method_getAliquot_should_return_response_with_stringWorkAliquot() throws JSONException, DataNotFoundException, ValidationException {
		when(transportationLotFacade.getAliquot(Mockito.any())).thenReturn(workAliquot);
		assertEquals(transportationResource.getAliquot(request, filterWorkAliquotJson),
				new Response().buildSuccess(builder.create().toJson(workAliquot)).toJson());
	}
}
