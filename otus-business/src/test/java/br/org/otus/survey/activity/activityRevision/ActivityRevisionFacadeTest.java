package br.org.otus.survey.activity.activityRevision;

import br.org.otus.model.User;
import br.org.otus.user.api.UserFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.activityRevision.ActivityRevisionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class ActivityRevisionFacadeTest {
    private static final String ACTIVITY_ID = "5c41cab016da480065be5d3c";
    private static final String ACTIVITY_REVISION_JSON = "{\"activityID\" : \"5c41c6b316da48006573a169\",\"reviewDate\" : \"17/01/2019\"}";
    private static final String USER_EMAIL = "otus@gmail.com";

    @InjectMocks
    private ActivityRevisionFacade activityRevisionFacade;

    @Mock
    private ActivityRevisionService activityRevisionService;
    @Mock
    private UserFacade userFacade;
    @Mock
    private User user;

    @Test
    public void method_should_verify_create_with_activityRevision() {
        when(userFacade.fetchByEmail(USER_EMAIL)).thenReturn(user);
        activityRevisionFacade.create(ACTIVITY_REVISION_JSON,USER_EMAIL);
        verify(activityRevisionService, times(1)).create(ACTIVITY_REVISION_JSON, user);
    }

    @Test
    public void method_should_verify_list_with() throws DataNotFoundException {
        when(activityRevisionService.list(ACTIVITY_ID)).thenReturn(new ArrayList<>());
        activityRevisionFacade.list(ACTIVITY_ID);
        verify(activityRevisionService, times(1)).list(ACTIVITY_ID);
    }
}
