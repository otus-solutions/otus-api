package br.org.otus.fieldCenter;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.model.FieldCenter;

import br.org.otus.fieldCenter.api.FieldCenterFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;

@Path("/center")
public class FieldCenterResource {
	@Inject
	private FieldCenterFacade fieldCenterFacade;

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	public String create(FieldCenter fieldCenter) {
		fieldCenterFacade.create(fieldCenter);
		return new Response().buildSuccess().toJson();
	}

	@GET
	@Secured
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public String list() {
		Response response = new Response();
		List<FieldCenter> fieldCenters = fieldCenterFacade.list();
		return response.setData(fieldCenters).toJson();
	}

	@POST
	@Secured
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(FieldCenter fieldCenter) {
		fieldCenterFacade.update(fieldCenter);
		return new Response().buildSuccess().toJson();
	}
}
