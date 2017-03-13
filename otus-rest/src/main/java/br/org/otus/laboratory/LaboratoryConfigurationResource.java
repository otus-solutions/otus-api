package br.org.otus.laboratory;

import br.org.otus.laboratory.service.LaboratoryConfigurationService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/laboratoryConfiguration")
public class LaboratoryConfigurationResource {

	@Inject
	private LaboratoryConfigurationService labConfigurationService;

	// TODO verificar necessidade deste resource para a tarefa atual
	@GET
	// @Secured
	@Path("/listTubes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getListTubes() {
		return labConfigurationService.getDefaultTubeList();
	}

	// @GET
	// // @Secured
	// @Path("/listCodes/{rn}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public List<String> getAllCodes(@PathParam("rn") Long rn) {
	// return labConfigurationService.getCodes(rn);
	// }

}
