package br.org.otus.persistence.pendency;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
public class UserActivityPendencyDtoTest {

  private final static int CURRENT_QUANTITY = 100;
  private final static int QUANTITY_TO_GET = 50;
  private static final String[] FIELDS_TO_ORDER = new String[] { "requester", "acronym" };
  private static final int ORDER_MODE = 1;

  private UserActivityPendencyDto userActivityPendencyDto;
  private String userActivityPendencyDtoJson;

  @Before
  public void SetUp(){
    Whitebox.setInternalState(userActivityPendencyDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(userActivityPendencyDto, "quantityToGet", QUANTITY_TO_GET);
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", FIELDS_TO_ORDER);
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", ORDER_MODE);

    userActivityPendencyDtoJson = UserActivityPendencyDto.serialize(userActivityPendencyDto);
  }

  @Test
  public void userActivityPendencyDto_not_should_be_null(){
    assertNotNull(userActivityPendencyDto);
    assertNotNull(userActivityPendencyDto.getFilterDto());
  }

  @Test
  public void deserialize_results(){
    assertEquals(CURRENT_QUANTITY, userActivityPendencyDto.getCurrentQuantity());
    assertEquals(QUANTITY_TO_GET, userActivityPendencyDto.getQuantityToGet());
  }

}
