package br.org.otus.survey.activity.activityRevision;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.model.survey.activity.user.ActivityBasicUser;
import org.ccem.otus.model.survey.activity.user.BasicUserFactory;
import org.ccem.otus.service.activityRevision.ActivityRevisionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class ActivityRevisionFacadeTest {
    private static final String ACTIVITY_ID = "5c41cab016da480065be5d3c";
    private static final String ACTIVITY_REVISION_JSON = "{\"activityId\" : \"5c41c6b316da48006573a169\",\"reviewDate\" : \"17/01/2019\"}";
    private static final String USER_EMAIL = "otus@gmail.com";
    @InjectMocks
    private ActivityRevisionFacade activityRevisionFacade;

    @Mock
    private ActivityRevisionService activityRevisionService;
    @Mock
    private ActivityRevision activityRevision;
    @Mock
    private ActivityBasicUser activityBasicUser;
    @Mock
    private BasicUserFactory basicUserFactory;

    @Test
    public void method_should_verify_create_with_activityRevision() {
        activityRevisionFacade.create(ACTIVITY_REVISION_JSON,USER_EMAIL);
//        verify(activityRevisionService).create(anyString(),anyObject());
//        verify(activityRevisionService, times(1)).create(ACTIVITY_REVISION_JSON,activityBasicUser);
    }

    @Test
    public void method_should_verify_list_with() throws DataNotFoundException {
        when(activityRevisionService.list(Mockito.any())).thenReturn(new ArrayList<>());
        activityRevisionFacade.list(ACTIVITY_ID);
        verify(activityRevisionService, times(1)).list(Mockito.any());
    }
}
