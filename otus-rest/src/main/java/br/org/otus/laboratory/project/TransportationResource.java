package br.org.otus.laboratory.project;

import br.org.otus.laboratory.project.api.TransportationLotFacade;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import br.org.otus.response.builders.System;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
//	@Secured // TODO: 10/08/17 uncomment
	@Path("/lot")
	public String create(String transportationLotJson) {
		TransportationLot transportationLot = TransportationLot.deserialize(transportationLotJson);
		TransportationLot createdLot = transportationLotFacade.create(transportationLot);
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
		boolean result = transportationLotFacade.delete(code);
		return new Response().buildSuccess(result).toJson();
	}

	@GET
//	@Secured
	@Path("/aliquots")
	public String getAliquots() {
		List<TransportationAliquot> aliquots= transportationLotFacade.getAliquots();
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		return builder.create().toJson(aliquots);
	}


}
