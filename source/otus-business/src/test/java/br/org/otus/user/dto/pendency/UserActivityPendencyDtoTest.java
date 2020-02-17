package br.org.otus.user.dto.pendency;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserActivityPendencyDtoTest {

  private final static int QUANTITY_PER_PAGE = 5;
  private final static int SKIP_QUANTITY = 50;

  private final static Integer RN_SORT_MODE = -1;
  private final static Integer EXTERNAL_ID_SORT_MODE = 1;
  private final static String ORDER_JSON = "{" +
    "\"rn\": " + RN_SORT_MODE + "," +
    "\"externalID\": " + EXTERNAL_ID_SORT_MODE +
    "}";

  private final static Boolean FINALIZED = Boolean.TRUE;
  private final static String RECEIVER = "xxx@otus";
  private final static String FILTER_JSON = "{" +
    "\"finalized\": " + FINALIZED.toString() + "," +
    "\"receiver\": " + RECEIVER +
    "}";

  private final static String JSON = "{" +
    "nPerPage: " + QUANTITY_PER_PAGE + "," +
    "skip: " + SKIP_QUANTITY + "," +
    "order: " + ORDER_JSON + "," +
    "filter: " + FILTER_JSON +
    "}";

  private UserActivityPendencyDto userActivityPendencyDto;

  @Before
  public void SetUp(){
    System.out.println(JSON);
    userActivityPendencyDto = UserActivityPendencyDto.deserialize(JSON);
  }

  @Test
  public void userActivityPendencyDto_not_should_be_null(){
    assertNotNull(userActivityPendencyDto);
    assertNotNull(userActivityPendencyDto.getOrderDto());
    assertNotNull(userActivityPendencyDto.getFilterDto());
  }

  @Test
  public void deserialize_results(){
    assertEquals(QUANTITY_PER_PAGE, userActivityPendencyDto.getCurrentQuantity());
    assertEquals(SKIP_QUANTITY, userActivityPendencyDto.getQuantityToGet());

    assertEquals(RN_SORT_MODE, userActivityPendencyDto.getOrderDto().getRnSortMode());
    assertEquals(EXTERNAL_ID_SORT_MODE, userActivityPendencyDto.getOrderDto().getExternalIDSortMode());

    assertEquals(FINALIZED, userActivityPendencyDto.getFilterDto().getFinalized());
    assertEquals(RECEIVER, userActivityPendencyDto.getFilterDto().getReceiver());
  }

}
