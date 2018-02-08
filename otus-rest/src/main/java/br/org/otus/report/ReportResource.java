package br.org.otus.report;

import br.org.otus.rest.Response;
import org.ccem.otus.model.ReportTemplate;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/report")
public class ReportResource {

    @Inject
    private ReportFacade reportFacade;

    @GET
    @Path("/{report}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getByRecruitmentNumber(@PathParam("report") long report) {
        return new Response().buildSuccess(reportFacade.getByReportId(report)).toJson();
    }
}
