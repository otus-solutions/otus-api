package br.org.otus.gateway;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class GatewayFacadeTest {
    private static String INFO_VARIABLE_PARAMS = "{\"recruitmentNumber\": 2047555,\"variables\":[{\"identification\": 123456,\"name\": \"tst1\",\"value\": \"Text\",\"sending\": 1},{\"identification\": null,\"name\": \"tst1\",\"value\": \"Text\",\"sending\": 9}]}";
    private static String CURRENT_VARIABLES_BY_MICROSERVICE = "[{\"identification\":123456,\"name\":\"tst1\",\"value\":\"Text\"}]";

    @InjectMocks
    private GatewayFacade gatewayFacade;
    @Mock
    private URL url;


    @Before
    public void setUp() throws Exception {
        //PowerMockito.whenNew(URL.class)


    }

    @Test
    public void getCurrentFacadeMethod_should_bring_currentVariableListJson() throws IOException {
        assertEquals(gatewayFacade.findCurrentVariables(INFO_VARIABLE_PARAMS).getData(), CURRENT_VARIABLES_BY_MICROSERVICE);
        assertEquals("variable", "variable");
    }

    @Test()
    public void getCurrentFacadeMethod_should_throw_exception_for_host_invalid_port() throws IOException {
        //assertEquals(gatewayFacade.findCurrentVariables(INFO_VARIABLE_PARAMS), CURRENT_VARIABLES_BY_MICROSERVICE);
        assertEquals("variable", "variable");
    }

}