package br.org.otus.laboratory.project;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.security.Secured;

@Path("/laboratory-project/transportation")
public class TransportationResource {

	
	@GET
	@Secured
	@Path("/lots")	
	public String getLots() {

		return null;
	}
	
	@POST
	@Secured
	@Path("/create")
	public String create(String transportationLotJson) {
		TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
		// Facade criar lote
		return null;
	}
	
	
	@PUT
	@Secured
	@Path("/update")
	public String update(String transportationLotJson) {
		TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
		return null;
	}
	
	@DELETE
	@Secured
	@Path("/remove/{id}")
	public String delete(@PathParam("id") String code) {
		
		return null;
	}
	
	
}
