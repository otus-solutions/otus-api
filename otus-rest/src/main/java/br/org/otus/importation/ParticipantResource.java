package br.org.otus.importation;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.model.Participant;
import org.ccem.otus.service.ParticipantService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("importation/participant")
public class ParticipantResource {

    @Inject
    private ParticipantService participantService;

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public String post(Set<Participant> participants) {
        participantService.create(participants);
        return new Response().buildSuccess().toJson();
    }
}
