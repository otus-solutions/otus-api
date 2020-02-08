package br.org.otus.laboratory.project;

import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotAliquotFilterDTO;
import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.project.api.ExamLotFacade;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
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
  @Path("/center-lots/{acronym}")
  public String getLots(@PathParam("acronym") String centerAcronym) {
    List<ExamLot> lots = examLotFacade.getLots(centerAcronym);
    GsonBuilder builder = ExamLot.getGsonBuilder();
    return new Response().buildSuccess(builder.create().toJson(lots)).toJson();
  }

  @POST
  @Secured
  @Path("/aliquot")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getAliquot(String examLotAliquotFilterJson) {
    ExamLotAliquotFilterDTO examLotAliquotFilterDTO = ExamLotAliquotFilterDTO.deserialize(examLotAliquotFilterJson);
    Aliquot aliquot = examLotFacade.getAliquot(examLotAliquotFilterDTO);
    GsonBuilder builder = ExamLot.getGsonBuilder();
    return new Response().buildSuccess(builder.create().toJson(aliquot)).toJson();
  }

  @GET
  @Secured
  @Path("/aliquots/{lotId}")
  public String getAliquots(@PathParam("lotId") String lotId) {
    List<Aliquot> aliquots = examLotFacade.getAliquots(lotId);
    GsonBuilder builder = ExamLot.getGsonBuilder();
    return new Response().buildSuccess(builder.create().toJson(aliquots)).toJson();
  }

  @GET
  @Secured
  @Path("/available-exams/{center}")
  @Consumes(MediaType.APPLICATION_JSON)
  public String getAvailableExams(@PathParam("center") String center) {
    LinkedHashSet<AliquoteDescriptor> availableExams = examLotFacade.getAvailableExams(center);
    return new Response().buildSuccess(availableExams).toJson();
  }
}
