package br.org.otus.laboratory.participant;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@Ignore
@RunWith(JUnit4.class)
public class LaboratoryParticipantTest {

  private ParticipantLaboratory laboratoryParticipant;

  @Before
  public void setup() {
    String json = ""
      + "{\"laboratory\":{"
      + "\"recruitment_number\":123456,"
      + "\"cq_group\":\"CQ_1\","
      + "\"tubes\":["
      + "{"
      + "\"code\":261116141,"
      + "\"type\":\"GEL\","
      + "\"aliquots\":[]"
      + "}],"
      + "\"exams\":[]"
      + "\"identification\":null"
      + "}}";

    laboratoryParticipant = new Gson().fromJson(json, ParticipantLaboratory.class);

  }

  @Test
  public void should_deserialize_for_TubeDefinition() {
    Long recNumber = laboratoryParticipant.getRecruitmentNumber();
    System.out.println(recNumber);
//		assertThat(tubeDef.get(0).getName(), equalTo("GEL") );
  }
}
