package br.org.otus.survey.activity.sharing;

import br.org.otus.security.AuthorizationHeaderReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({AuthorizationHeaderReader.class})
public class ActivitySharingResourceTest {

  private static final String USER_TOKEN = "123456";
  private static final String ACTIVITY_ID = "5a33cb4a28f10d1043710f7d";
  private static final String SHARED_URL = "https://otus";


  @InjectMocks
  private ActivitySharingResource activitySharingResource;
  @Mock
  private ActivitySharingFacade activitySharingFacade;
  @Mock
  private HttpServletRequest request;


  @Test
  public void getSharedURL_method_should_return_url(){
    mockContextToSetUserToken();
    when(activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN)).thenReturn(SHARED_URL);
    assertEquals(
      encapsulateExpectedResponse(SHARED_URL),
      activitySharingResource.getSharedURL(request, ACTIVITY_ID)
    );
    verify(activitySharingFacade, Mockito.times(1)).getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }


  @Test
  public void renovateSharedURL_method_should_return_url(){
    mockContextToSetUserToken();
    when(activitySharingFacade.renovateSharedURL(ACTIVITY_ID, USER_TOKEN)).thenReturn(SHARED_URL);
    assertEquals(
      encapsulateExpectedResponse(SHARED_URL),
      activitySharingResource.renovateSharedURL(request, ACTIVITY_ID)
    );
    verify(activitySharingFacade, Mockito.times(1)).renovateSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test
  public void deleteSharedURL_method_should_invoke_activitySharingFacade(){
    activitySharingResource.deleteSharedURL(ACTIVITY_ID);
    verify(activitySharingFacade, Mockito.times(1)).deleteSharedURL(ACTIVITY_ID);
  }


  private String encapsulateExpectedResponse(String data) {
    return "{\"data\":\"" + data + "\"}";
  }

  private void mockContextToSetUserToken() {
    mockStatic(AuthorizationHeaderReader.class);
    when(request.getHeader(Mockito.any())).thenReturn(USER_TOKEN);
    when(AuthorizationHeaderReader.readToken(Mockito.any())).thenReturn(USER_TOKEN);
  }

}
