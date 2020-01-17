package br.org.otus.laboratory.project;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.project.api.TransportationLotFacade;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import br.org.otus.rest.Response;
import br.org.otus.security.user.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ TransportationAliquotFiltersDTO.class, AuthorizationHeaderReader.class,  TransportationLot.class})
public class TransportationResourceTest {

	private static final String EMAIL = "otus@otus.com";
	private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9";
	private static final String TRANSPORTATION_LOT_JSON = "{objectType: TransportationLot, code:'', fieldCenter:{acronym:'SP'}, shipmentDate:'2018-11-09T14:19:00.000Z', processingDate:'2018-11-09T14:19:00.000Z', operator:'otus@otus.com', aliquotList:[], aliquotsInfo:[]}";
	private static final String ID_CODE = "abc123";
	private static final String RESPONSE_VALID = "{\"data\":true}";
	private static final String ALIQUOT_JSON = "{recruitmentNumber: 1090810, fieldCenter: {acronym:'SP'}, code: 363123470 ,role:'STORAGE', objectType:'Aliquot'}";
	private static final String FILTER_ALIQUOT_JSON = "{recruitmentNumber: 1090810, fieldCenter: 'SP', code: 363123470 ,role:'STORAGE', objectType:'Aliquot'}";

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

	private TransportationAliquotFiltersDTO transportationAliquotFiltersDTO;
	private List<Aliquot> aliquots;
	private GsonBuilder builder;
	private Aliquot aliquot;
	private TransportationLot transportationLot;
	private TransportationLot createdLot;
	private TransportationLot updateLot;
	private List<TransportationLot> lots;

	@Before
	public void setUp() {

		when(request.getHeader(anyString())).thenReturn(TOKEN);
		mockStatic(AuthorizationHeaderReader.class);
		when(AuthorizationHeaderReader.readToken(TOKEN)).thenReturn(TOKEN);
		when(securityContext.getSession(TOKEN)).thenReturn(sessionIdentifier);
		when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
		when(authenticationData.getUserEmail()).thenReturn(EMAIL);
		transportationLot = TransportationLot.deserialize(TRANSPORTATION_LOT_JSON);
		aliquot = aliquot.deserialize(ALIQUOT_JSON);
		aliquots = Arrays.asList(aliquot);
		mockStatic(TransportationAliquotFiltersDTO.class);
		transportationAliquotFiltersDTO = TransportationAliquotFiltersDTO.deserialize(FILTER_ALIQUOT_JSON);
	}

	@Test
	public void getLotsMethod_should_return_TransportationLotList_in_Response() {
		builder = TransportationLot.getGsonBuilder();
		lots = Arrays.asList(transportationLot);
		when(transportationLotFacade.getLots()).thenReturn(lots);
		assertEquals(transportationResource.getLots(),
				new Response().buildSuccess(builder.create().toJson(lots)).toJson());
	}


	@Test
	public void createMethod_should_return_TransportationLot_in_Response() {
		createdLot = TransportationLot.deserialize(TRANSPORTATION_LOT_JSON);
		when(transportationLotFacade.create(transportationLot, EMAIL)).thenReturn(createdLot);
		assertEquals(transportationResource.create(request, TRANSPORTATION_LOT_JSON),
				new Response().buildSuccess(TransportationLot.serialize(createdLot)).toJson());
	}

	@Test
	public void updateMethod_should_return_TransportationLot_in_Response() {
		updateLot = TransportationLot.deserialize(TRANSPORTATION_LOT_JSON);
		when(transportationLotFacade.update(transportationLot)).thenReturn(updateLot);
		assertEquals(transportationResource.update(TRANSPORTATION_LOT_JSON),
				new Response().buildSuccess(TransportationLot.serialize(updateLot)).toJson());
	}

	@Test
	public void deleteMethod_should_return_confirmation_in_Response() {
		assertEquals(RESPONSE_VALID, transportationResource.delete(ID_CODE));
		verify(transportationLotFacade, times(1)).delete(ID_CODE);
	}

	@Test
	public void getAliquotsByPeriodMethod_should_return_ListAliquots_in_response() throws JSONException {
		builder = Aliquot.getGsonBuilder();
		when(transportationLotFacade.getAliquotsByPeriod(transportationAliquotFiltersDTO)).thenReturn(aliquots);
		assertEquals(transportationResource.getAliquotsByPeriod(request, FILTER_ALIQUOT_JSON),
				new Response().buildSuccess(builder.create().toJson(aliquots)).toJson());
	}

	@Test
	public void getAliquotMethod_should_return_Aliquot_in_Response () {
		builder = Aliquot.getGsonBuilder();
		when(transportationLotFacade.getAliquot(transportationAliquotFiltersDTO)).thenReturn(aliquot);
		assertEquals(transportationResource.getAliquot(request, FILTER_ALIQUOT_JSON),
				new Response().buildSuccess(builder.create().toJson(aliquot)).toJson());
	}

	@Test
	public void getAliquotsMethod_should_return_ListAliquots_in_response() {
		builder = Aliquot.getGsonBuilder();
		when(transportationLotFacade.getAliquots()).thenReturn(aliquots);
		assertEquals(transportationResource.getAliquots(),
				new Response().buildSuccess(builder.create().toJson(aliquots)).toJson());
	}
}
