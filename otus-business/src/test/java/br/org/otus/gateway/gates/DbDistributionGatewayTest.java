package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.resource.DBDistributionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DbDistributionGateway.class})
public class DbDistributionGatewayTest {
    private static String INFO_VARIABLE_PARAMS = "{\"recruitmentNumber\": \"4107\",\"variables\":[{\"name\": \"tst1\",\"value\": \"Text\",\"sending\": \"1\"},{\"name\": \"tst1\",\"value\": \"Text\",\"sending\": \"2\"}]}";
    private static String CURRENT_VARIABLES_BY_MICROSERVICE = "{\"variables\":[{\"name\": \"var2\",\"sending\": \"1\"}]}";
    private static String URL_DBDistribution = "http://localhost:8081/api/findVariables";
    private static String HOST = "http://localhost:";
    private static String PORT = "8081";

    @InjectMocks
    private DbDistributionGateway dbDistributionGateway;

    @Mock
    private JsonPOSTUtility jsonPOSTUtility;

    private DBDistributionMicroServiceResources dbDistributionMicroServiceResources = PowerMockito.spy(new DBDistributionMicroServiceResources());

    @Mock
    private URL requestURL;

    @Test
    public void findVariables() throws Exception {

        PowerMockito.whenNew(DBDistributionMicroServiceResources.class).withNoArguments().thenReturn(dbDistributionMicroServiceResources);
        Whitebox.setInternalState(dbDistributionMicroServiceResources, "HOST", HOST);
        Whitebox.setInternalState(dbDistributionMicroServiceResources, "PORT", PORT);
        PowerMockito.when(dbDistributionMicroServiceResources.getFindVariableAddress()).thenReturn(requestURL);

        PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);


        final GatewayResponse response = dbDistributionGateway.findVariables(INFO_VARIABLE_PARAMS);
//        assertEquals(CURRENT_VARIABLES_BY_MICROSERVICE,response.getData());
    }


    @Test
    public void uploadDatabase() {
    }

    @Test
    public void uploadVariableTypeCorrelation() {
    }
}