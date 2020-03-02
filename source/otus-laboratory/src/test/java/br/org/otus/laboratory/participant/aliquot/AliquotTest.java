package br.org.otus.laboratory.participant.aliquot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;

@RunWith(PowerMockRunner.class)
public class AliquotTest {
  public static final String ALIQUOT_JSON_STRING = "{\"tubeCode\":\"331002551\",\"transportationLotId\":null,\"examLotId\":null,\"examLotData\":null,\"recruitmentNumber\":3051442,\"sex\":\"F\",\"fieldCenter\":{\"name\":null,\"code\":null,\"acronym\":\"MG\",\"country\":null,\"state\":null,\"address\":null,\"complement\":null,\"zip\":null,\"phone\":null,\"backgroundColor\":null,\"borderColor\":null},\"birthdate\":{\"objectType\":\"ImmutableDate\",\"value\":\"1977-05-04 00:00:00.000\"},\"locationPoint\":null,\"objectType\":\"Aliquot\",\"code\":\"334000000\",\"name\":\"BIOCHEMICAL_SERUM\",\"container\":\"PALLET\",\"role\":\"EXAM\",\"aliquotCollectionData\":{\"objectType\":\"AliquotCollectionData\",\"metadata\":\"\",\"operator\":\"nando.souza97@hotmail.com\",\"time\":\"2017-10-09T18:30:06.811Z\",\"processing\":null},\"aliquotHistory\":null}";
  public static final String TUBE_CODE = "331002551";
  public static final Long RECRUITMENT_NUMBER = 3051442L;
  public static final FieldCenter FIELD_CENTER = FieldCenter.fromJson("{\"name\":null,\"code\":null,\"acronym\":\"MG\",\"country\":null,\"state\":null,\"address\":null,\"complement\":null,\"zip\":null,\"phone\":null,\"backgroundColor\":null,\"borderColor\":null}");
  public static final ObjectId EXAM_LOT_ID = new ObjectId();
  public static final ObjectId TRANSPORTATION_LOT_ID = new ObjectId();
  public static final ImmutableDate BIRTHDATE = new ImmutableDate("1977-05-04 00:00:00.000");
  private Aliquot aliquot;
  private Aliquot aliquotFromJson;
  private AliquotContainer aliquotContainer;
  private AliquotRole aliquotRole;
  @Mock
  private AliquotCollectionData aliquotCollectionData;

  @Before
  public void setUp() {
    aliquotFromJson = Aliquot.deserialize(ALIQUOT_JSON_STRING);
    aliquot = new Aliquot();
    aliquot.setBirthdate(BIRTHDATE);
    aliquot.setSex(Sex.F);
    aliquot.setExamLotId(EXAM_LOT_ID);
    aliquot.setRecruitmentNumber(RECRUITMENT_NUMBER);
    aliquot.setFieldCenter(FIELD_CENTER);
    aliquot.setTubeCode(TUBE_CODE);
    aliquot.setTransportationLotId(TRANSPORTATION_LOT_ID);
  }

  @Test
  public void unitTest_for_evoke_getters() {
    assertEquals(TUBE_CODE, aliquot.getTubeCode());
    assertEquals(RECRUITMENT_NUMBER, aliquot.getRecruitmentNumber());
    assertEquals(TRANSPORTATION_LOT_ID, aliquot.getTransportationLotId());
    assertEquals(Sex.F, aliquot.getSex());
    assertEquals(EXAM_LOT_ID, aliquot.getExamLotId());
    assertEquals(FIELD_CENTER, aliquot.getFieldCenter());
    assertEquals(BIRTHDATE, aliquot.getBirthdate());
  }

  @Test
  public void method_getCode_should_return_code() {
    assertEquals(TUBE_CODE, aliquot.getTubeCode());
  }

  @Test
  public void method_serialize_should_return_aliquotJson() {
    assertEquals(Aliquot.serialize(aliquotFromJson), ALIQUOT_JSON_STRING);
  }
}
