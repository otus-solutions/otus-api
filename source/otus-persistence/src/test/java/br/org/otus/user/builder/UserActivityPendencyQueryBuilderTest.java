package br.org.otus.user.builder;

import br.org.otus.user.pendency.builder.UserActivityPendencyQueryBuilder;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserActivityPendencyQueryBuilderTest {

  private static UserActivityPendencyQueryBuilder builder = null;
  private static final String USER_ROLE = "requester";
  private static final String USER_EMAIL = "requester@otus.com";

  @Before
  public void setUp() {
    builder = new UserActivityPendencyQueryBuilder();
  }

  @Test
  public void getListAllPendenciesByUserQuery() {
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
  public void getListOpenedPendenciesByUserQuery() {
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
      ",{\"$ne\":[\"" + UserActivityPendencyQueryBuilder.FINALIZED_STATUS + "\",{\"$arrayElemAt\":[\"$statusHistory.name\",-1.0]}]}" +
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
  public void getListDonePendenciesByUserQuery() {
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
