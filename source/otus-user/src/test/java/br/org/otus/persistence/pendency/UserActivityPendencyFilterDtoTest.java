package br.org.otus.persistence.pendency;

//import br.org.otus.persistence.pendency.dto.UserActivityPendencyRequestFilterDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserActivityPendencyFilterDtoTest {

  private final static Long RN = 123L;
  private final static Integer EXTERNAL_ID = 1;
  private final static String REQUESTER = "user@otus.com";
  private final static String STATUS = "FINALIZED";
  private final static String JSON = "{" +
    "\"requester\": [" + REQUESTER + "]," +
    "\"status\": " + STATUS +
    "}";

//  private UserActivityPendencyRequestFilterDto userActivityPendencyRequestFilterDto;
//
//  @Before
//  public void setUp(){
//    userActivityPendencyRequestFilterDto = UserActivityPendencyRequestFilterDto.deserialize(JSON);
//    System.out.println(userActivityPendencyRequestFilterDto.toString());
//  }
//
//  @Test
//  public void testDeserializeNotNull(){
//    assertNotNull(userActivityPendencyRequestFilterDto);
//  }
//
//  @Test
//  public void testDeserializeResult(){
//    assertEquals(STATUS, userActivityPendencyRequestFilterDto.getStatus());
//  }
}
