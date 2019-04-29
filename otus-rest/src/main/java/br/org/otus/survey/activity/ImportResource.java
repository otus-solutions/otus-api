package br.org.otus.survey.activity;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.survey.activity.activityRevision.ActivityRevisionFacade;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.survey.activity.api.ActivityFacade;
import org.ccem.otus.model.survey.activity.activityImport.ActivityImportDTO;
import org.ccem.otus.model.survey.activity.activityImport.ActivityImportResultDTO;

import java.util.ArrayList;
import java.util.List;

@Path("activities/import")
public class ImportResource {

    @Inject
    private ActivityFacade activityFacade;


    @PUT
//  @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{acronym}/{version}")
    public String importActivities(String surveyActivities, @PathParam("acronym") String acronym, @PathParam("version") int rn) {
        ActivityImportDTO activityImportDTO = ActivityImportDTO.deserialize(surveyActivities);
        List<ActivityImportResultDTO> failImports = activityFacade.importActivities(activityImportDTO);
        return new Response().buildSuccess(failImports).toJson();
    }

}
