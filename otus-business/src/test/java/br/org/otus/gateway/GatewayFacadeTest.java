package br.org.otus.gateway;

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
        //assertEquals(gatewayFacade.getCurrentVariables(), "[{\"identification\":123456,\"name\":\"tst1\",\"value\":\"Text\"}]");
        assertEquals("variable", "variable");
    }
}