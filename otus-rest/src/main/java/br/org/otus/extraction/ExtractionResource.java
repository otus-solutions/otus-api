package br.org.otus.extraction;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.org.otus.security.Secured;

//TODO review url
@Path("data-extraction")
public class ExtractionResource {
	
	@Inject
	private ExtractionFacadeService extractionFacadeService;
	
	@GET
	@Secured
	@Path("/{acronym}")
//	@Produces(MediaType.APPLICATION_JSON)
	public String extractActivities(@PathParam("acronym") String acronym) {
		extractionFacadeService.createActivityExtraction(acronym);
		return null;
	}

}
