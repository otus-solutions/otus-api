package br.org.otus.user.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.user.api.pendency.UserActivityPendencyFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AuthorizationHeaderReader.class})
public class UserActivityPendencyResourceTest {

  private static final String REQUESTER_EMAIL = "requester@otus.com";
  private static final String TOKEN = "123456";
  private static final String NEW_PENDENCY_DATA = "{" +
    "\"objectType\":\"userActivityPendency\"," +
    "\"creationDate\":\"2019-12-19T19:31:08.570Z\"," +
    "\"dueDate\":\"2019-12-19T19:31:08.570Z\"," +
    "\"requester\":\"" + REQUESTER_EMAIL + "\"," +
    "\"receiver\":\"ativ_created22@otus.com\"," +
    "\"activityInfo\":{" +
    "\"id\":\"5dd55b8e415e9c6746ca2da1\"," +
    "\"acronym\":\"ABR\"," +
    "\"recruitmentNumber\":5001007" +
    "}" +
    "}";
  private static final String EXPECTED_EMPTY_RESPONSE = "{\"data\":true}";
  private static final String EXPECTED_CREATE_RESPONSE = "12355b8e415e9c6746ca2da1";

  @InjectMocks
  private UserActivityPendencyResource userActivityPendencyResource;
  @Mock
  private UserActivityPendencyFacade userActivityPendencyFacade;
  @Mock
  private HttpServletRequest request;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private SessionIdentifier session;
  @Mock
  private AuthenticationData authenticationData;

  private UserActivityPendency userActivityPendency;
  private List<UserActivityPendency> userActivityPendencies;
  private String userActivityPendencyJson;

  @Before
  public void setUp() {
    userActivityPendency = new UserActivityPendency();
    userActivityPendencies = asList(userActivityPendency);
    userActivityPendencyJson = UserActivityPendency.serialize(userActivityPendency);
  }

  private List<String> getPendenciesJson(){
    List<String> pendenciesJson = new ArrayList<>();
    // CREATED
    pendenciesJson.add("{" +
      "\"_id\":\"5e0658135b4ff40f8916d2b5\"," +
      "\"objectType\":\"userActivityPendency\"," +
      "\"creationDate\":\"2019-12-19T19:31:08.570Z\"," +
      "\"dueDate\":\"2019-12-19T19:31:08.570Z\"," +
      "\"requester\":\"flavia.avila@ufrgs.br\"," +
      "\"receiver\":\"ativ_created@otus.com\"," +
      "\"activityInfo\":{" +
      "\"id\":\"5a33cb4a28f10d1043710f7d\"," +
      "\"acronym\":\"CSI\"," +
      "\"recruitmentNumber\":5005283" +
      "}" +
      "}");
    // FINALIZED
    pendenciesJson.add("{" +
      "\"_id\":\"5e065caf5b4ff40f8916d2b6\"," +
      "\"objectType\":\"userActivityPendency\"," +
      "\"creationDate\":\"2019-12-30T19:31:08.570Z\"," +
      "\"dueDate\":\"2019-11-20T19:31:08.570Z\"," +
      "\"requester\":\"flavia.avila@ufrgs.br\"," +
      "\"receiver\":\"ativ_finalized@otus.com\"," +
      "\"activityInfo\":{" +
      "\"id\":\"5a37fa6428f10d1043711055\"," +
      "\"acronym\":\"MED3\"," +
      "\"recruitmentNumber\":4028230" +
      "}" +
      "}");
    // CREATED
    pendenciesJson.add("{" +
      "\"_id\":\"5e06659bf9d5924d303db335\"," +
      "\"objectType\":\"userActivityPendency\"," +
      "\"creationDate\":\"2019-12-19T19:31:08.570Z\"," +
      "\"dueDate\":\"2020-12-19T19:31:08.570Z\"," +
      "\"requester\":\"flavia.avila@ufrgs.br\"," +
      "\"receiver\":\"ativ_created2@otus.com\"," +
      "\"activityInfo\":{" +
      "\"id\":\"5a33cb4b28f10d1043710f82\"," +
      "\"acronym\":\"DSOC\"," +
      "\"recruitmentNumber\":5005283" +
      "}" +
      "}");
    // FINALIZED
    pendenciesJson.add("{" +
      "\"_id\":\"5e0e33d9dd944271e8e700f5\"," +
      "\"objectType\":\"userActivityPendency\"," +
      "\"creationDate\":\"2019-12-30T19:31:08.570Z\"," +
      "\"dueDate\":\"2018-11-20T19:31:08.570Z\"," +
      "\"requester\":\"flavia.avila@ufrgs.br\"," +
      "\"receiver\":\"ativ_finalized2@otus.com\"," +
      "\"activityInfo\":{" +
      "\"id\":\"5a37fa6428f10d104371105c\"," +
      "\"acronym\":\"ECGC\"," +
      "\"recruitmentNumber\":5000891" +
      "}" +
      "}");
    return pendenciesJson;
  }

  private List<UserActivityPendency> deserializePendenciesList(List<String> pendenciesJson){
    List<UserActivityPendency> deserialiazed = new ArrayList<>();
    for (String pendencyJson : pendenciesJson) {
      deserialiazed.add(UserActivityPendency.deserialize(pendencyJson));
    }
    return deserialiazed;
  }

  private String encapsulateExpectedResponse(String data){
    return "{\"data\":" + data + "}";
  }

  @Test
  public void createMethod_should_be_create_pendency_by_userActivityPendencyFacade() {
    mockStatic(AuthorizationHeaderReader.class);
    when(request.getHeader(Mockito.any())).thenReturn(TOKEN);
    when(AuthorizationHeaderReader.readToken(Mockito.any())).thenReturn(TOKEN);
    when(securityContext.getSession(TOKEN)).thenReturn(session);
    when(session.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(REQUESTER_EMAIL);

    when(userActivityPendencyFacade.create(REQUESTER_EMAIL, NEW_PENDENCY_DATA)).thenReturn(EXPECTED_CREATE_RESPONSE);
    assertEquals(
      encapsulateExpectedResponse("\"" + EXPECTED_CREATE_RESPONSE + "\""),
      userActivityPendencyResource.create(request, NEW_PENDENCY_DATA));
    verify(userActivityPendencyFacade, Mockito.times(1)).create(REQUESTER_EMAIL, NEW_PENDENCY_DATA);
  }

  @Test
  public void updateMethod_should_be_update_pendency_by_userActivityPendencyFacade() {
    String pendencyJson = getPendenciesJson().get(0);
    String pendencyId = UserActivityPendency.deserialize(pendencyJson).getId().toString();
    assertEquals(EXPECTED_EMPTY_RESPONSE, userActivityPendencyResource.update(pendencyId, pendencyJson));
    verify(userActivityPendencyFacade, Mockito.times(1)).update(pendencyId, pendencyJson);
  }

  @Test
  public void deleteMethod_should_be_delete_pendency_by_userActivityPendencyFacade() {
    String pendencyId = UserActivityPendency.deserialize(getPendenciesJson().get(0)).getId().toString();
    assertEquals(EXPECTED_EMPTY_RESPONSE, userActivityPendencyResource.delete(pendencyId));
    verify(userActivityPendencyFacade, Mockito.times(1)).delete(pendencyId);
  }

  @Test
  public void getByActivityIdMethod_should_return_pendency_by_activityInfo() {
    String pendencyJson = getPendenciesJson().get(0);
    userActivityPendency = UserActivityPendency.deserialize(pendencyJson);
    String activityId = userActivityPendency.getActivityInfo().getId().toString();
    when(userActivityPendencyFacade.getByActivityId(activityId)).thenReturn(userActivityPendency);
    assertEquals(
      encapsulateExpectedResponse(pendencyJson),
      userActivityPendencyResource.getByActivityId(activityId));
  }

  @Test
  public void listAllPendenciesMethod_should_return_all_pendencies() {
    List<String> pendenciesJson = getPendenciesJson();
    userActivityPendencies = deserializePendenciesList(pendenciesJson);
    when(userActivityPendencyFacade.listAllPendencies()).thenReturn(userActivityPendencies);
    assertEquals(
      encapsulateExpectedResponse("["+ String.join(",", pendenciesJson) + "]"),
      userActivityPendencyResource.listAllPendencies());
  }

  @Test
  public void listOpenedPendenciesMethod_should_return_only_opened_pendencies() {
    List<String> pendenciesJson = getPendenciesJson();
    pendenciesJson = pendenciesJson.stream().filter(pendencyJson -> pendencyJson.contains("created"))
      .collect(Collectors.toList());

    userActivityPendencies = deserializePendenciesList(pendenciesJson);
    when(userActivityPendencyFacade.listOpenedPendencies()).thenReturn(userActivityPendencies);
    assertEquals(
      encapsulateExpectedResponse("["+ String.join(",", pendenciesJson) + "]"),
      userActivityPendencyResource.listOpenedPendencies());
  }

  @Test
  public void listDonePendencies_should_return_only_done_pendencies() {
    List<String> pendenciesJson = getPendenciesJson();
    pendenciesJson = pendenciesJson.stream().filter(pendencyJson -> pendencyJson.contains("finalized"))
      .collect(Collectors.toList());

    userActivityPendencies = deserializePendenciesList(pendenciesJson);
    when(userActivityPendencyFacade.listDonePendencies()).thenReturn(userActivityPendencies);
    assertEquals(
      encapsulateExpectedResponse("["+ String.join(",", pendenciesJson) + "]"),
      userActivityPendencyResource.listDonePendencies());
  }

}
