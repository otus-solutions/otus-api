package br.org.otus.user.dto.pendency;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyRequestOrderDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserActivityPendencyRequestOrderDtoTest {

  private final static Integer RN_SORT_MODE = -1;
  private final static Integer EXTERNAL_ID_SORT_MODE = 1;
  private final static String JSON = "{" +
    "\"rn\": " + RN_SORT_MODE + "," +
    "\"externalID\": " + EXTERNAL_ID_SORT_MODE +
    "}";

  private UserActivityPendencyRequestOrderDto userActivityPendencyRequestOrderDto;

  @Before
  public void setUp(){
    userActivityPendencyRequestOrderDto = UserActivityPendencyRequestOrderDto.deserialize(JSON);
    System.out.println(userActivityPendencyRequestOrderDto.toString());
  }

  @Test
  public void testDeserializeNotNull(){
    assertNotNull(userActivityPendencyRequestOrderDto);
  }

  @Test
  public void testDeserializeResult(){
    assertEquals(RN_SORT_MODE, userActivityPendencyRequestOrderDto.getRnSortMode());
    assertEquals(EXTERNAL_ID_SORT_MODE, userActivityPendencyRequestOrderDto.getExternalIDSortMode());
  }
}
