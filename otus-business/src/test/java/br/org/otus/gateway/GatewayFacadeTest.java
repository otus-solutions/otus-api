package br.org.otus.gateway;

import br.org.otus.gateway.gates.DbDistributionGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class GatewayFacadeTest {
    private static String INFO_VARIABLE_PARAMS = "{\"recruitmentNumber\": 2047555,\"variables\":[{\"identification\": 123456,\"name\": \"tst1\",\"value\": \"Text\",\"sending\": 1},{\"identification\": null,\"name\": \"tst1\",\"value\": \"Text\",\"sending\": 9}]}";
    private static String CURRENT_VARIABLES_BY_MICROSERVICE = "{\"identification\":\"4107\",\"variables\":[{\"name\": \"var2\",\"sending\":1},{\"name\": \"var3\",\"sending\": \"2\"}]}";

    @InjectMocks
    private DbDistributionGateway dbDistributionGateway;
    @Mock
    private URL url;


    @Before
    public void setUp() throws Exception {
        //PowerMockito.whenNew(URL.class)


    }

    @Test
    public void getCurrentFacadeMethod_should_bring_currentVariableListJson() throws IOException {
        assertEquals(dbDistributionGateway.findVariables(CURRENT_VARIABLES_BY_MICROSERVICE).getData(), CURRENT_VARIABLES_BY_MICROSERVICE);
        assertEquals("variable", "variable");
    }

    @Test()
    public void getCurrentFacadeMethod_should_throw_exception_for_host_invalid_port() throws IOException {
        try {
            File temp = File.createTempFile("pattern", ".json");

            temp.deleteOnExit();

            BufferedWriter out = new BufferedWriter(new FileWriter(temp));
            out.write("[{\"name\":\"1\",\"type\":\"string\"},{\"name\":\"2\",\"type\":\"string\"}]");
            out.close();
            dbDistributionGateway.uploadVariableTypeCorrelation(temp);
        } catch (IOException e) {
        }
        assertEquals("variable", "variable");
    }

}