package br.org.otus.user.builder;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import br.org.otus.user.pendency.builder.UserActivityPendencyQueryBuilder;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.zip.DataFormatException;

import static org.junit.Assert.assertEquals;

public class UserActivityPendencyQueryBuilderTest {

  private static final String USER_ROLE = "requester";
  private static final String USER_EMAIL = "requester@otus.com";

  private static final String ACTIVITY_ID_FIELD = UserActivityPendencyQueryBuilder.ACTIVITY_ID_FIELD;
  private static final String ACTIVITY_INFO = UserActivityPendencyQueryBuilder.ACTIVITY_INFO;
  private static final String ACTIVITY_NAME_FIELD = UserActivityPendencyQueryBuilder.ACTIVITY_NAME_FIELD;
  private static final String ACTIVITY_ACRONYM_FIELD = UserActivityPendencyQueryBuilder.ACTIVITY_ACRONYM_FIELD;
  private static final String ACTIVITY_RN_FIELD = UserActivityPendencyQueryBuilder.ACTIVITY_RN_FIELD;

  private UserActivityPendencyQueryBuilder builder = null;

  @Before
  public void setUp() {
    builder = new UserActivityPendencyQueryBuilder();
  }

  @Test
  public void getAllPendencies() throws DataFormatException {
    final int CURRENT_QUANTITY = 100;
    final int QUANTITY_TO_GET = 50;
    final String[] FIELDS_TO_ORDER = new String[] { "rn", "dueDate" };
    final Integer ASCENDING_ORDER_MODE = 1;
    final String ACRONYM = "XXX";
    UserActivityPendencyFilterDto userActivityPendencyFilterDto = new UserActivityPendencyFilterDto();
    Whitebox.setInternalState(userActivityPendencyFilterDto, "acronym", ACRONYM);
    Whitebox.setInternalState(userActivityPendencyFilterDto, "requester", new String[]{ USER_EMAIL });

    UserActivityPendencyDto userActivityPendencyDto = new UserActivityPendencyDto();
    Whitebox.setInternalState(userActivityPendencyDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(userActivityPendencyDto, "quantityToGet", QUANTITY_TO_GET);
    Whitebox.setInternalState(userActivityPendencyDto, "fieldsToOrder", FIELDS_TO_ORDER);
    Whitebox.setInternalState(userActivityPendencyDto, "orderMode", ASCENDING_ORDER_MODE);
    Whitebox.setInternalState(userActivityPendencyDto, "filterDto", userActivityPendencyFilterDto);

    final String EXPECTED_QUERY = "[{" +
      "\"$lookup\":{" +
      "\"from\":\"activity\"," +
      "\"let\":{\"" + ACTIVITY_ID_FIELD+"\":\"$"+ACTIVITY_ID_FIELD+"\"}," +
      "\"pipeline\":[{" +
      "\"$match\":{" +
      "\"$expr\":{" +
      "\"$and\":[" +
      "{\"$eq\":[\"$"+ACTIVITY_ACRONYM_FIELD+"\",\""+ ACRONYM +"\"]}," +
      "{\"$eq\":[\"$$"+ACTIVITY_ID_FIELD+"\",\"$_id\"]}," +
      "{\"$eq\":[false,\"$isDiscarded\"]}]}}}," +
      "{\"$project\":{" +
      "\"recruitmentNumber\":\"$" + ACTIVITY_RN_FIELD + "\"," +
      "\"name\":\"$"+ACTIVITY_NAME_FIELD+"\"," +
      "\"acronym\":\"$"+ACTIVITY_ACRONYM_FIELD+"\"," +
      "\"lastStatusName\":{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}," +
      "\"externalID\":1.0}}],"+
      "\"as\":\""+ACTIVITY_INFO+"\"}},"+
      "{\"$match\":{"+
      "\"$expr\":{"+
      "\"$and\":["+
      "{\"$in\":[\"$" + USER_ROLE + "\",[\"" + USER_EMAIL + "\"]]}," +
      "{\"$gt\":[{\"$size\":\"$"+ACTIVITY_INFO+"\"},0.0]}"+
      "]}}},{"+
      "\"$addFields\":{\""+ACTIVITY_INFO+"\":{\"$arrayElemAt\":[\"$"+ACTIVITY_INFO+"\",0.0]}}},"+
      "{\"$skip\":"+CURRENT_QUANTITY+".0},"+
      "{\"$limit\":"+QUANTITY_TO_GET+".0},"+
      "{\"$sort\":{\""+ACTIVITY_INFO+".recruitmentNumber\":1.0,\"dueDate\":1.0}}]";
    assertEquals(EXPECTED_QUERY,
      new GsonBuilder().create().toJson(builder.getAllPendenciesWithFiltersQuery(userActivityPendencyDto)));
  }

  @Test
  public void getAllPendenciesByUserQuery() {
    final String EXPECTED_QUERY = "[{" +
      "\"$lookup\":{" +
      "\"from\":\"activity\"," +
      "\"let\":{\"activityId\":\"$activityId\"}," +
      "\"pipeline\":[" +
      "{\"$match\":{" +
      "\"$expr\":{" +
      "\"$and\":[" +
      "{\"$eq\":[\"$$activityId\",\"$_id\"]}," +
      "{\"$eq\":[false,\"$isDiscarded\"]}" +
      "]}}}," +
      "{\"$project\":{" +
      "\"recruitmentNumber\":\"$participantData.recruitmentNumber\"," +
      "\"name\":\"$surveyForm.name\"," +
      "\"acronym\":\"$surveyForm.acronym\"," +
      "\"lastStatusName\":{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}," +
      "\"externalID\":1.0" +
      "}}]," +
      "\"as\":\"activityInfo\"}}," +
      "{\"$match\":{" +
      "\"$expr\":{" +
      "\"$and\":[" +
      "{\"$eq\":[\"$" + USER_ROLE + "\",\"" + USER_EMAIL + "\"]}," +
      "{\"$gt\":[{\"$size\":\"$activityInfo\"},0.0]}" +
      "]}}}," +
      "{\"$addFields\":{\"activityInfo\":{\"$arrayElemAt\":[\"$activityInfo\",0.0]}}}]";
    assertEquals(EXPECTED_QUERY,
      new GsonBuilder().create().toJson(builder.getAllPendenciesByUserQuery(USER_ROLE, USER_EMAIL)));
  }

  @Test
  public void getOpenedPendenciesByUserQuery() {
    final String EXPECTED_QUERY = "[{" +
      "\"$lookup\":{" +
      "\"from\":\"activity\"," +
      "\"let\":{\"activityId\":\"$activityId\"}," +
      "\"pipeline\":[" +
      "{\"$match\":{" +
      "\"$expr\":{" +
      "\"$and\":[" +
      "{\"$eq\":[\"$$activityId\",\"$_id\"]}," +
      "{\"$eq\":[false,\"$isDiscarded\"]}," +
      "{\"$ne\":[\"" + UserActivityPendencyQueryBuilder.FINALIZED_STATUS + "\",{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}]}" +
      "]}}}," +
      "{\"$project\":{" +
      "\"recruitmentNumber\":\"$participantData.recruitmentNumber\"," +
      "\"name\":\"$surveyForm.name\"," +
      "\"acronym\":\"$surveyForm.acronym\"," +
      "\"lastStatusName\":{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}," +
      "\"externalID\":1.0" +
      "}}]," +
      "\"as\":\"activityInfo\"}}," +
      "{\"$match\":{" +
      "\"$expr\":{" +
      "\"$and\":[" +
      "{\"$eq\":[\"$" + USER_ROLE + "\",\"" + USER_EMAIL + "\"]}," +
      "{\"$gt\":[{\"$size\":\"$activityInfo\"},0.0]}" +
      "]}}}," +
      "{\"$addFields\":{\"activityInfo\":{\"$arrayElemAt\":[\"$activityInfo\",0.0]}}}]";
    assertEquals(EXPECTED_QUERY,
      new GsonBuilder().create().toJson(builder.getOpenedPendenciesByUserQuery(USER_ROLE, USER_EMAIL)));
  }

  @Test
  public void getDonePendenciesByUserQuery() {
    final String EXPECTED_QUERY = "[{" +
      "\"$lookup\":{" +
      "\"from\":\"activity\"," +
      "\"let\":{\"activityId\":\"$activityId\"}," +
      "\"pipeline\":[" +
      "{\"$match\":{" +
      "\"$expr\":{" +
      "\"$and\":[" +
      "{\"$eq\":[\"$$activityId\",\"$_id\"]}," +
      "{\"$eq\":[false,\"$isDiscarded\"]}" +
      ",{\"$eq\":[\"" + UserActivityPendencyQueryBuilder.FINALIZED_STATUS + "\",{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}]}" +
      "]}}}," +
      "{\"$project\":{" +
      "\"recruitmentNumber\":\"$participantData.recruitmentNumber\"," +
      "\"name\":\"$surveyForm.name\"," +
      "\"acronym\":\"$surveyForm.acronym\"," +
      "\"lastStatusName\":{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}," +
      "\"externalID\":1.0" +
      "}}]," +
      "\"as\":\"activityInfo\"}}," +
      "{\"$match\":{" +
      "\"$expr\":{" +
      "\"$and\":[" +
      "{\"$eq\":[\"$" + USER_ROLE + "\",\"" + USER_EMAIL + "\"]}," +
      "{\"$gt\":[{\"$size\":\"$activityInfo\"},0.0]}" +
      "]}}}," +
      "{\"$addFields\":{\"activityInfo\":{\"$arrayElemAt\":[\"$activityInfo\",0.0]}}}]";
    assertEquals(EXPECTED_QUERY,
      new GsonBuilder().create().toJson(builder.getDonePendenciesByUserQuery(USER_ROLE, USER_EMAIL)));
  }

}