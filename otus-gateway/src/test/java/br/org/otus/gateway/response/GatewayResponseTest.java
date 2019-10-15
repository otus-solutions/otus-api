package br.org.otus.gateway.response;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(PowerMockRunner.class)
public class GatewayResponseTest {
  private static Object DATA = "test";
  private static String GATEWAY_DATA = "{\"data\":\"test\"}";
  public String gatewayJson;

  @InjectMocks
  private GatewayResponse gatewayResponse;

  private GsonBuilder gsonBuilder;

  private Gson gson;

  @Before
  public void setUp() throws Exception {
    gatewayResponse.setData(DATA);

    gsonBuilder = new GsonBuilder();

    gson = gsonBuilder.create();

    gatewayJson = gatewayResponse.toJson();
  }

  @Test
  public void getDataMethod_should_return_object() {
    assertEquals(DATA, gatewayResponse.getData());
  }

  @Test
  public void toJsonMethod_should_return_json() {
    assertEquals(GATEWAY_DATA, gatewayResponse.toJson());
  }

  @Test
  public void toJson1Method_should_return_create_builder_string() {
    assertEquals(gatewayJson, gatewayResponse.toJson(gsonBuilder));
  }

  @Test
  public void buildSuccessMethod_should_return_this() {
    assertEquals(DATA, gatewayResponse.buildSuccess(DATA).getData());
  }

  @Test
  public void buildSuccess1Method_should_return_boolean() {
    assertEquals(true, gatewayResponse.buildSuccess().getData());
  }
}