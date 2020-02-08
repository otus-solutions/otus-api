package org.ccem.otus.model.monitoring.laboratory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class LaboratoryProgressDTOTest {

  private static String EXPECTED_SERIALIZE = "{\"orphanExamsProgress\":[{\"title\":\"POTÁSSIO - URINA AMOSTRA ISOLADA\",\"orphans\":84}]}";
  private static String orphanExamsProgressDTOJson = "{\"_id\" : {},\"orphanExamsProgress\" : [{\"title\" : \"POTÁSSIO - URINA AMOSTRA ISOLADA\",\"orphans\" : 84.0}]}";

  LaboratoryProgressDTO laboratoryProgressDTO = new LaboratoryProgressDTO();

  @Test
  public void serialize() {
    assertTrue(LaboratoryProgressDTO.deserialize(orphanExamsProgressDTOJson) instanceof LaboratoryProgressDTO);
  }

  @Test
  public void deserialize() {
    LaboratoryProgressDTO orphanExamsProgressDTO = LaboratoryProgressDTO.deserialize(orphanExamsProgressDTOJson);
    assertEquals(EXPECTED_SERIALIZE, LaboratoryProgressDTO.serialize(orphanExamsProgressDTO));
  }
}