package br.org.otus.laboratory.project;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
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
	public String create(@Context HttpServletRequest request, String examLotJson) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();

		ExamLot examLot = ExamLot.deserialize(examLotJson);
		ExamLot createdLot = examLotFacade.create(examLot, userEmail);

		return new Response().buildSuccess(ExamLot.serialize(createdLot)).toJson();
	}

	@PUT
	@Secured
	public String update(String examLotJson) {
		ExamLot examLot = ExamLot.deserialize(examLotJson);
		ExamLot updatedLot = examLotFacade.update(examLot);
		return new Response().buildSuccess(ExamLot.serialize(updatedLot)).toJson();
	}

	@DELETE
	@Secured
	@Path("/{id}")
	public String delete(@PathParam("id") String code) {
		examLotFacade.delete(code);
		return new Response().buildSuccess().toJson();
	}

	@GET
	@Secured
	public String getLots() {
		List<ExamLot> lots = examLotFacade.getLots();
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

	@GET
	@Secured
	@Path("/available-exams/{center}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAvailableAliquots(@PathParam("center") String center) {
		List<AliquoteDescriptor> aliquoteDescriptors = examLotFacade.getAvailableAliquots(center);
		return new Response().buildSuccess(aliquoteDescriptors).toJson();
	}
}
