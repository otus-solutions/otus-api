package br.org.otus.extraction;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.security.Secured;

@Path("activity-extraction")
public class ExtractionResource {

	@Inject
	private ExtractionFacadeService extractionFacadeService;

	@GET
	@Secured
	@Path("/{acronym}")
	@Produces(MediaType.APPLICATION_JSON)
	public String extractActivities(@PathParam("acronym") String acronym) {
		return extractionFacadeService.createActivityExtraction(acronym);
	}

}
