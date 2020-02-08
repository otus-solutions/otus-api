package org.ccem.otus.model.survey.activity.dto;

import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckerUpdatedDTOTest {

  private static final String checkerUpdated = "{\"id\":\"5c0e5d41e69a69006430cb75\",\"activityStatus\":{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_OFFLINE\",\"date\":\"2018-12-10T12:33:29.007Z\",\"user\":{\"name\":\"Otus\",\"surname\":\"Solutions\",\"extraction\":true,\"extractionIps\":[\"999.99.999.99\"],\"phone\":\"21987654321\",\"fieldCenter\":{},\"email\":\"otus@gmail.com\",\"admin\":false,\"enable\":true,\"meta\":{\"revision\":0,\"created\":0,\"version\":0},\"$loki\":2}}}";
  private static final String checkerExpected = "{\"id\":\"5c0e5d41e69a69006430cb75\",\"activityStatus\":{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_OFFLINE\",\"date\":\"2018-12-10T12:33:29.007Z\",\"user\":{\"name\":\"Otus\",\"surname\":\"Solutions\",\"phone\":\"21987654321\",\"email\":\"otus@gmail.com\"}}}";

  private static final String id = "5c0e5d41e69a69006430cb75";
  private CheckerUpdatedDTO checkerUpdatedDTO;

  @Before
  public void setUp() throws Exception {
    checkerUpdatedDTO = CheckerUpdatedDTO.deserialize(checkerUpdated);
  }

  @Test
  public void getIdMethod_should_return_value_of_id_by_objectDeserialize() {
    assertEquals(id, checkerUpdatedDTO.getId());
  }

  @Test
  public void getActivityStatus_should_return_instance_of_ActivityStatus() {
    assertTrue(checkerUpdatedDTO.getActivityStatus() instanceof ActivityStatus);
  }

  @Test
  public void serializeStaticMethod_should_convert_object_in_string() {
    assertEquals(checkerExpected, CheckerUpdatedDTO.serialize(checkerUpdatedDTO));
  }

  @Test
  public void deserializeStaticMethod_should_convert_to_dtoModel() {
    assertTrue(CheckerUpdatedDTO.deserialize(checkerUpdated) instanceof CheckerUpdatedDTO);
  }
}