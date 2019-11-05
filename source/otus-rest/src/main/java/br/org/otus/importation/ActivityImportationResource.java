package br.org.otus.importation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.otus.importation.activity.ActivityImportationFacade;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.importation.activity.ActivityImportDTO;
import org.ccem.otus.importation.activity.ActivityImportResultDTO;

import java.util.List;

@Path("activities/import")
public class ActivityImportationResource {

    @Inject
    private ActivityImportationFacade activityImportationFacade;

    @PUT
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{acronym}/{version}")
    public String importActivities(String surveyActivities, @PathParam("acronym") String acronym, @PathParam("version") int version) {
        ActivityImportDTO activityImportDTO = ActivityImportDTO.deserialize(surveyActivities);
        List<ActivityImportResultDTO> failImports = activityImportationFacade.importActivities(acronym, version, activityImportDTO);
        return new Response().buildSuccess(failImports).toJson();
    }

}
