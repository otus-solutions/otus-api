package org.ccem.otus.model;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class RequestParametersTest {

    private RequestParameters requestParameters;

    private ObjectId objectId = new ObjectId("5a9199056ddc4f48a340b3ec");;

    private String requestParametersJson = "{\"recruitmentNumber\":322148795,\"reportId\":\"5a9199056ddc4f48a340b3ec\"}";

    @Test
    public void method_deserialize_should_return_requestParameters(){
        RequestParameters requestParameters = RequestParameters.deserialize(requestParametersJson);
        assertEquals(322148795, requestParameters.getRecruitmentNumber().longValue());
        assertEquals(objectId, requestParameters.getReportId());
    }
}
