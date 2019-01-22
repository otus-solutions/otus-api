package br.org.otus.survey.activity.activityRevision;

import org.ccem.otus.service.activityRevision.ActivityRevisionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class ActivityRevisionFacadeTest {
    private static final String ID = "{\"data\":\"5c41cab016da480065be5d3c}";
    private static final String ACTIVITY_ID = "5c41cab016da480065be5d3c";
    private static final String ACTIVITY_REVISION_JSON = "{\"activityId\" : \"5c41c6b316da48006573a169\"," + "\"reviewDate\" : \"17/01/2019\"}";
    @Mock
    private ActivityRevisionService activityRevisionService;
    @InjectMocks
    private ActivityRevisionFacade activityRevisionFacade;

    @Test
    public void method_should_verify_create_with_activityRevision() {
        when(activityRevisionService.create(Mockito.any())).thenReturn(ID);
        activityRevisionFacade.create(ACTIVITY_REVISION_JSON);
        verify(activityRevisionService, times(1)).create(Mockito.any());
    }

    @Test
    public void method_should_verify_list_with() {
        when(activityRevisionService.list(Mockito.any())).thenReturn(new ArrayList<>());
        activityRevisionFacade.list(ACTIVITY_ID);
        verify(activityRevisionService, times(1)).list(Mockito.any());
    }
}
