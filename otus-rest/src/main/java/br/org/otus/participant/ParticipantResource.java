package br.org.otus.participant;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.GsonBuilder;

import br.org.otus.model.User;
import br.org.otus.participant.api.ParticipantFacade;
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
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll(@Context HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
		User user = userFacade.fetchByEmail(userEmail);
		
		// TODO: it needs to use Response.toJson() - reminder: data:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
		return builder.create().toJson(participantFacade.list(user.getFieldCenter()));
	}

	@GET
	@Path("/{rn}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Participant getByRecruitmentNumber(@PathParam("rn") long rn) {
		return participantFacade.getByRecruitmentNumber(rn);
	}

}
