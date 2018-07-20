package br.org.otus.participant;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.Participant;

import br.org.otus.model.User;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.user.api.UserFacade;

@Path("/participants")
public class ParticipantResource {

	@Inject
	private ParticipantFacade participantFacade;
	@Inject
	private UserFacade userFacade;
	@Inject
	private SecurityContext securityContext;
	
	@GET
	@Secured
	public String getAll(@Context HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
		User user = userFacade.fetchByEmail(userEmail);
		return new Response().buildSuccess(participantFacade.list(user.getFieldCenter())).toCustomJson(Participant.getGsonBuilder());
	}

	@GET
	@Path("/{rn}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Participant getByRecruitmentNumber(@PathParam("rn") long rn) {
		return participantFacade.getByRecruitmentNumber(rn);
	}
	
	@POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(@Context HttpServletRequest request, String participantJson) throws DataNotFoundException, ValidationException {
      return new Response().buildSuccess(participantFacade.create(participantJson)).toCustomJson(Participant.getGsonBuilder());
    }

}
