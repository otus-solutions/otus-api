package br.org.otus.extraction;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

//TODO review url
@Path("data-extraction")
public class ExtractionResource {

	@Inject
	private ExtractionFacadeService extractionFacadeService;

	@GET
	@Secured
	@Path("/{acronym}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response extractActivities(@PathParam("acronym") String acronym, @Context HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=activity_" + acronym + ".csv");
		// response.setContentLength(csv.length);
		extractionFacadeService.createActivityExtraction(acronym);
		return null;
	}
}
