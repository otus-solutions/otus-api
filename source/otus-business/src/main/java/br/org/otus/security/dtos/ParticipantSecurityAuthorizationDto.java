package br.org.otus.security.dtos;

import br.org.otus.user.dto.FieldCenterDTO;
import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.time.LocalDateTime;

public class ParticipantSecurityAuthorizationDto implements Dto {

  @Equalization(name = "recruitmentNumber")
  private Long recruitmentNumber;

  @Equalization(name = "name")
  private String name;

  @Equalization(name = "sex")
  private Sex sex;

  @Equalization(name = "birthdate")
  private ImmutableDate birthdate;

  private FieldCenterDTO fieldCenter;

  @Equalization(name = "email")
  private String email;

  private String token;

  @Override
  public Boolean isValid() {
    return Boolean.TRUE;
  }

  public FieldCenterDTO getFieldCenter() {
    return fieldCenter;
  }

  public void setFieldCenter(FieldCenterDTO fieldCenter) {
    this.fieldCenter = fieldCenter;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getToken(){
    return this.token;
  }

  public static String serialize(ParticipantSecurityAuthorizationDto participantSecurityAuthorizationDto) {
    return Participant.getGsonBuilder().create().toJson(participantSecurityAuthorizationDto);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder;
  }
}
