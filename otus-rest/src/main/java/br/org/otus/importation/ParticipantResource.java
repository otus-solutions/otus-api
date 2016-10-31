package br.org.otus.importation;

import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("importation/participant")
public class ParticipantResource {

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public String post(List<String> participants) {
        participants.stream().forEach(System.out::println);
        return new Response().buildSuccess().toJson();
    }
}
