package org.ccem.otus.model.dataSources.participant;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSourceResult;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class ParticipantDataSourceResultTest {

  private ParticipantDataSourceResult participantDataSourceResult;
  private String participantDatasourceJson = "{\"recruitmentNumber\":123456789,\"name\":\"Joao\",\"sex\":\"masc\",\"birthdate\":{\"objectType\":\"ImmutableDate\",\"value\":\"2018-02-22 00:00:00.000\"},\"fieldCenter\":{\"acronym\":\"RS\"}}";

  @Before
  public void setUp() {
    FieldCenter fieldCenterInstance = new FieldCenter();
    Whitebox.setInternalState(fieldCenterInstance, "acronym", "RS");

    ImmutableDate immutableDateInstance = new ImmutableDate("2018-02-22 00:00:00.000");


    participantDataSourceResult = new ParticipantDataSourceResult();
    Whitebox.setInternalState(participantDataSourceResult, "name", "Joao");
    Whitebox.setInternalState(participantDataSourceResult, "sex", "masc");
    Whitebox.setInternalState(participantDataSourceResult, "recruitmentNumber", (long) 123456789);
    Whitebox.setInternalState(participantDataSourceResult, "fieldCenter", fieldCenterInstance);
    Whitebox.setInternalState(participantDataSourceResult, "birthdate", immutableDateInstance);
  }


  @Test
  public void method_serialize_should_return_json_of_participantDatasourceJson() {
    assertEquals(participantDatasourceJson, ParticipantDataSourceResult.serialize(participantDataSourceResult));
  }

  @Test
  public void method_deserialize_should_return_participantDataSourceResult() {
    ParticipantDataSourceResult result = ParticipantDataSourceResult.deserialize(participantDatasourceJson);
    assertEquals("Birthdate", participantDataSourceResult.getBirthdate().getValue(), result.getBirthdate().getValue());
    assertEquals("FieldCenter", participantDataSourceResult.getFieldCenter().getAcronym(), result.getFieldCenter().getAcronym());
    assertEquals("Name", participantDataSourceResult.getName(), result.getName());
    assertEquals("RecruitmentNumber", participantDataSourceResult.getRecruitmentNumber(), result.getRecruitmentNumber());
    assertEquals("Sex", participantDataSourceResult.getSex(), result.getSex());
  }

}
