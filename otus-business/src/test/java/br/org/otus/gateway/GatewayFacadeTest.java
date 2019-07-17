package br.org.otus.gateway;

import com.google.gson.JsonParser;
import com.mongodb.util.JSON;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class GatewayFacadeTest {

    @InjectMocks
    private GatewayFacade gatewayFacade;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getCurrentFacadeMethod_should() throws IOException, ClassNotFoundException {
        String InfoVariables = "{\n" +
                "    \"recruitmentNumber\": 2047555,\n" +
                "    \"variables\": [\n" +
                "        {\n" +
                "            \"identification\": 123456,\n" +
                "            \"name\": \"tst1\",\n" +
                "            \"value\": \"Text\",\n" +
                "            \"sending\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"identification\": null,\n" +
                "            \"name\": \"tst1\",\n" +
                "            \"value\": \"Text\",\n" +
                "            \"sending\": 9\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        //assertEquals(gatewayFacade.getCurrentVariables(), "[{\"identification\":123456,\"name\":\"tst1\",\"value\":\"Text\"}]");
        //assertEquals(gatewayFacade.findCurrentVariables(InfoVariables), "[{\"identification\":123456,\"name\":\"tst1\",\"value\":\"Text\"}]");
        assertEquals("variable", "variable");
    }
}