package br.org.otus.laboratory.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.api.TransportationLotFacade;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;

@Path("/laboratory-project/transportation")
public class TransportationResource {

	@Inject
	private TransportationLotFacade transportationLotFacade;

	@Inject
	private SecurityContext securityContext;
	
	@GET
	@Secured
	@Path("/lots")	
	public String getLots() {
		List<TransportationLot> lots = transportationLotFacade.getLots();
		GsonBuilder builder = TransportationLot.getGsonBuilder();
		return new Response().buildSuccess(builder.create().toJson(lots)).toJson();
	}
	
	@POST
	@Secured
	@Path("/lot")
	public String create(@Context HttpServletRequest request, String transportationLotJson) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

		TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
		TransportationLot createdLot = transportationLotFacade.create(transportationLot, userEmail);
		return new Response().buildSuccess(TransportationLot.serialize(createdLot)).toJson();
	}

	@PUT
	@Secured
	@Path("/lot")
	public String update(String transportationLotJson) {
		TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
		TransportationLot updatedLot = transportationLotFacade.update(transportationLot);
		return new Response().buildSuccess(TransportationLot.serialize(updatedLot)).toJson();
	}
	
	@DELETE
	@Secured
	@Path("/lot/{id}")
	public String delete(@PathParam("id") String code) {
		transportationLotFacade.delete(code);
		return new Response().buildSuccess().toJson();
	}	
	
	
	@POST
	@Secured
	@Path("/aliquots")
	public String getWorkAliquotList(@Context HttpServletRequest request, String filterWorkAliquotJson) throws JSONException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();			
		
		String filterWorkAliquot = filterWorkAliquotJson.toString();
		JSONObject filter = new JSONObject(filterWorkAliquot);		
		String code = (String) filter.get("code");
		String initialDate = (String) filter.get("initialDate");
		String finalDate = (String) filter.get("finalDate");
		String fieldCenter = (String) filter.get("fieldCenter");
		String role = (String) filter.get("role");	
		String aliquotCodes = (String) filter.get("aliquotList");		
		String[]aliquotCodeList = aliquotCodes.trim().split("/[^0-9]/,");		
	
		List<WorkAliquot> workAliquots= transportationLotFacade.getAliquotsByPeriod(code, initialDate, finalDate, fieldCenter, role, aliquotCodeList);
		GsonBuilder builder = WorkAliquot.getGsonBuilder();
		return new Response().buildSuccess(builder.create().toJson(workAliquots)).toJson();			

	}

	@GET
	@Secured
	@Path("/aliquots")
	public String getAliquots() {
		List<WorkAliquot> aliquots= transportationLotFacade.getAliquots();
		GsonBuilder builder = TransportationLot.getGsonBuilder();
		return new Response().buildSuccess(builder.create().toJson(aliquots)).toJson();
	}


}
