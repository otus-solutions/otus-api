package br.org.otus.user.builder;

import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyFilterDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyOrderDto;
import br.org.otus.user.pendency.builder.UserActivityPendencyQueryBuilder;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;

public class UserActivityPendencyQueryBuilderTest {

  private static final String USER_ROLE = "requester";
  private static final String USER_EMAIL = "requester@otus.com";

  private static final String ACTIVITY_ID_FIELD = UserActivityPendencyQueryBuilder.ACTIVITY_ID_FIELD;
  private static final String ACTIVITY_INFO = UserActivityPendencyQueryBuilder.ACTIVITY_INFO;
  private static final String ACTIVITY_NAME_FIELD = UserActivityPendencyQueryBuilder.ACTIVITY_NAME_FIELD;
  private static final String ACTIVITY_ACRONYM_FIELD = UserActivityPendencyQueryBuilder.ACTIVITY_ACRONYM_FIELD;
  private static final String ACTIVITY_RN_FIELD = UserActivityPendencyQueryBuilder.ACTIVITY_RN_FIELD;
  private static final String FINALIZED_STATUS = UserActivityPendencyQueryBuilder.FINALIZED_STATUS;
  private static final String NO_EXTERNAL_ID = UserActivityPendencyQueryBuilder.NO_EXTERNAL_ID;

  private UserActivityPendencyQueryBuilder builder = null;

  @Before
  public void setUp() {
    builder = new UserActivityPendencyQueryBuilder();
  }

  @Test
  public void getAllPendencies() {
    final int CURRENT_QUANTITY = 100;
    final int QUANTITY_TO_GET = 50;
    final String[] FIELDS_TO_ORDER = new String[] { "rn", "dueDate" };
    final Integer ASCENDING_ORDER_MODE = 1;
    final String ACRONYM = "XXX";

    UserActivityPendencyOrderDto orderDto = new UserActivityPendencyOrderDto();
    Whitebox.setInternalState(orderDto, "fieldsToOrder", FIELDS_TO_ORDER);
    Whitebox.setInternalState(orderDto, "mode", ASCENDING_ORDER_MODE);

    UserActivityPendencyFilterDto filterDto = new UserActivityPendencyFilterDto();
    Whitebox.setInternalState(filterDto, "acronym", ACRONYM);
    Whitebox.setInternalState(filterDto, "requester", new String[]{ USER_EMAIL });

    UserActivityPendencyDto userActivityPendencyDto = new UserActivityPendencyDto();
    Whitebox.setInternalState(userActivityPendencyDto, "currentQuantity", CURRENT_QUANTITY);
    Whitebox.setInternalState(userActivityPendencyDto, "quantityToGet", QUANTITY_TO_GET);
    Whitebox.setInternalState(userActivityPendencyDto, "orderDto", orderDto);
    Whitebox.setInternalState(userActivityPendencyDto, "filterDto", filterDto);

    final String EXPECTED_QUERY = "[" +
      getLookupPipeline("{\"$eq\":[\"$"+ACTIVITY_ACRONYM_FIELD+"\",\""+ ACRONYM +"\"]},") +
      getProjectPipeline() +
      getPendencyFilterPipeline("{\"$in\":[\"$" + USER_ROLE + "\",[\"" + USER_EMAIL + "\"]]},") +
      addSelectedFieldsFromActivityLookupResult() + "," +
      "{\"$sort\":{\""+ACTIVITY_INFO+".recruitmentNumber\":1.0,\"dueDate\":1.0}}," +
      "{\"$skip\":"+CURRENT_QUANTITY+".0},"+
      "{\"$limit\":"+QUANTITY_TO_GET+".0}" +
      "]";

    assertEquals(EXPECTED_QUERY,
      new GsonBuilder().create().toJson(builder.getAllPendenciesWithFiltersQuery(userActivityPendencyDto)));
  }

  @Test
  public void getAllPendenciesByUserQuery() {
    final String EXPECTED_QUERY = "[" +
      getLookupPipeline("") +
      getProjectPipeline() +
      getPendencyFilterPipeline("{\"$eq\":[\"$" + USER_ROLE + "\",\"" + USER_EMAIL + "\"]},") +
      addSelectedFieldsFromActivityLookupResult() +
      "]";

    assertEquals(EXPECTED_QUERY,
      new GsonBuilder().create().toJson(builder.getAllPendenciesByUserQuery(USER_ROLE, USER_EMAIL)));
  }

  @Test
  public void getOpenedPendenciesByUserQuery() {
    final String EXPECTED_QUERY = "[" +
      getLookupPipeline("{\"$ne\":[\"" + FINALIZED_STATUS + "\",{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}]},") +
      getProjectPipeline() +
      getPendencyFilterPipeline("{\"$eq\":[\"$" + USER_ROLE + "\",\"" + USER_EMAIL + "\"]}," ) +
      addSelectedFieldsFromActivityLookupResult() +
      "]";
    assertEquals(EXPECTED_QUERY,
      new GsonBuilder().create().toJson(builder.getOpenedPendenciesByUserQuery(USER_ROLE, USER_EMAIL)));
  }

  @Test
  public void getDonePendenciesByUserQuery() {
    final String EXPECTED_QUERY = "[" +
      getLookupPipeline("{\"$eq\":[\"" + FINALIZED_STATUS + "\",{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}]},") +
      getProjectPipeline() +
      getPendencyFilterPipeline("{\"$eq\":[\"$" + USER_ROLE + "\",\"" + USER_EMAIL + "\"]}," ) +
      addSelectedFieldsFromActivityLookupResult() +
      "]";
    assertEquals(EXPECTED_QUERY,
      new GsonBuilder().create().toJson(builder.getDonePendenciesByUserQuery(USER_ROLE, USER_EMAIL)));
  }


  private  String getLookupPipeline(String activityFilterEquations){
    return
      "{\"$lookup\":{" +
        "\"from\":\"activity\"," +
        "\"let\":{\"" + ACTIVITY_ID_FIELD+"\":\"$"+ACTIVITY_ID_FIELD+"\"}," +
        "\"pipeline\":[{" +
        "\"$match\":{" +
        "\"$expr\":{" +
        "\"$and\":[" +
        activityFilterEquations +
        "{\"$eq\":[\"$$"+ACTIVITY_ID_FIELD+"\",\"$_id\"]}," +
        "{\"$eq\":[false,\"$isDiscarded\"]}]}}},";
  }

  private String getProjectPipeline(){
    return "{\"$project\":{" +
      "\"recruitmentNumber\":\"$" + ACTIVITY_RN_FIELD + "\"," +
      "\"name\":\"$"+ACTIVITY_NAME_FIELD+"\"," +
      "\"acronym\":\"$"+ACTIVITY_ACRONYM_FIELD+"\"," +
      "\"lastStatusName\":{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}," +
      "\"externalID\":{\"$ifNull\":[\"$externalID\",\""+NO_EXTERNAL_ID+"\"]}}}]," +
      "\"as\":\""+ACTIVITY_INFO+"\"}},";
  }

  private String getPendencyFilterPipeline(String pendencyFilterEquations){
    return
      "{\"$match\":{"+
        "\"$expr\":{"+
        "\"$and\":["+
        pendencyFilterEquations +
        "{\"$gt\":[{\"$size\":\"$"+ACTIVITY_INFO+"\"},0.0]}"+
        "]}}},";
  }

  private String addSelectedFieldsFromActivityLookupResult(){
    return "{\"$addFields\":{\""+ACTIVITY_INFO+"\":{\"$arrayElemAt\":[\"$"+ACTIVITY_INFO+"\",0.0]}}}";
  }

}