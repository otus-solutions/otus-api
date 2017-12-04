package br.org.otus.laboratory.project;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.api.ExamLotFacade;
import br.org.otus.laboratory.project.exam.ExamLot;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;

@Path("/laboratory-project/exam-lot")
public class ExamResource {

	@Inject
	private ExamLotFacade examLotFacade;

	@Inject
	private SecurityContext securityContext;

	@POST
	@Secured
	@Path("/create")
	public String create(@Context HttpServletRequest request, String examLotJson) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token))
				.getAuthenticationData().getUserEmail();

		ExamLot examLot = ExamLot.deserialize(examLotJson);
		ExamLot createdLot = examLotFacade.create(examLot, userEmail);

		return new Response().buildSuccess(ExamLot.serialize(createdLot)).toJson();
	}

	@PUT
	@Secured
	@Path("/update")
	public String update(String examLotJson) {
		ExamLot examLot = ExamLot.deserialize(examLotJson);
		ExamLot updatedLot = examLotFacade.update(examLot);
		return new Response().buildSuccess(ExamLot.serialize(updatedLot)).toJson();
	}

	@DELETE
	@Secured
	@Path("/delete/{id}")
	public String delete(@PathParam("id") String code) {
		examLotFacade.delete(code);
		return new Response().buildSuccess().toJson();
	}

	@GET
	@Secured
	@Path("/lots")
	public String getLots() {
		List<WorkAliquot> lots = examLotFacade.getLots();
		GsonBuilder builder = ExamLot.getGsonBuilder();
		return new Response().buildSuccess(builder.create().toJson(lots)).toJson();
	}

	@GET
	@Secured
	@Path("/aliquots")
	public String getAliquots() {
		List<WorkAliquot> aliquots = examLotFacade.getAliquots();
		GsonBuilder builder = ExamLot.getGsonBuilder();
		return new Response().buildSuccess(builder.create().toJson(aliquots)).toJson();
	}
}
