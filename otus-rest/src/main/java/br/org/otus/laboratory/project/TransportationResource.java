package br.org.otus.laboratory.project;

import br.org.otus.laboratory.project.api.TransportationLotFacade;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;

@Path("/laboratory-project/transportation")
public class TransportationResource {

	@Inject
	private TransportationLotFacade transportationLotFacade;
	
	@GET
//	@Secured
	@Path("/lots")	
	public String getLots() {
		ArrayList<TransportationLot> lots = transportationLotFacade.getLots();
		return null;
	}
	
	@POST
//	@Secured
	@Path("/create")
	public String create(String transportationLotJson) {
		TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
		TransportationLot createdLot = transportationLotFacade.create(transportationLot);
		return new Response().buildSuccess(TransportationLot.serialize(createdLot)).toJson();
	}
	
	
	@PUT
	@Secured
	@Path("/update")
	public String update(String transportationLotJson) {
		TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
		TransportationLot updatedLot = transportationLotFacade.update(transportationLot);
		return new Response().buildSuccess(TransportationLot.serialize(updatedLot)).toJson();
	}
	
	@DELETE
	@Secured
	@Path("/remove/{id}")
	public String delete(@PathParam("id") String code) {
		boolean result = transportationLotFacade.delete(code);
		return new Response().buildSuccess(result).toJson();
	}
	
	
}
