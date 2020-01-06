package br.org.otus.user.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.service.pendency.UserActivityPendencyService;
import br.org.otus.user.api.pendency.UserActivityPendencyFacade;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class UserActivityPendencyFacadeTest {

  private static final String USER_EMAIL = "requester@otus.com";
  private static final String PENDENCY_ID = "5e0658135b4ff40f8916d2b5";
  private static final String ACTIVITY_ID = "5a33cb4a28f10d1043710f7d";

  @InjectMocks
  private UserActivityPendencyFacade userActivityPendencyFacade;
  @Mock
  private UserActivityPendencyService userActivityPendencyService;

  private UserActivityPendency userActivityPendency;
  private List<UserActivityPendency> userActivityPendencies;
  private String userActivityPendencyJson;
  private ObjectId userActivityPendencyOID;
  private ValidationException validationException;
  private DataNotFoundException dataNotFoundException;
  private MemoryExcededException memoryExcededException;

  @Before
  public void setUp() throws Exception {
    userActivityPendency = new UserActivityPendency();
    userActivityPendencies = asList(userActivityPendency);
    userActivityPendencyJson = UserActivityPendency.serialize(userActivityPendency);
    userActivityPendencyOID = new ObjectId(PENDENCY_ID);
    validationException = PowerMockito.spy(new ValidationException());
    dataNotFoundException = PowerMockito.spy(new DataNotFoundException());
    memoryExcededException = PowerMockito.spy(new MemoryExcededException(""));
  }

  @Test
  public void createMethod_should_invoke_create_from_userActivityPendencyService() {
    when(userActivityPendencyService.create(USER_EMAIL, userActivityPendency)).thenReturn(userActivityPendencyOID);
    assertEquals(PENDENCY_ID, userActivityPendencyFacade.create(USER_EMAIL, userActivityPendencyJson));
  }

  @Test
  public void updateMethod_should_invoke_update_from_userActivityPendencyService() throws DataNotFoundException {
    userActivityPendencyFacade.update(PENDENCY_ID, userActivityPendencyJson);
    verify(userActivityPendencyService, times(1)).update(userActivityPendencyOID, userActivityPendency);
  }

  @Test (expected = HttpResponseException.class)
  public void updateMethod_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(userActivityPendencyService, "update", userActivityPendencyOID, userActivityPendency);
    userActivityPendencyFacade.update(PENDENCY_ID, userActivityPendencyJson);
  }

  @Test
  public void deleteMethod_should_invoke_delete_from_userActivityPendencyService() throws DataNotFoundException {
    userActivityPendencyFacade.delete(PENDENCY_ID);
    verify(userActivityPendencyService, times(1)).delete(userActivityPendencyOID);
  }

  @Test (expected = HttpResponseException.class)
  public void deleteMethod_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(userActivityPendencyService, "delete", userActivityPendencyOID);
    userActivityPendencyFacade.delete(PENDENCY_ID);
  }

  @Test
  public void getByActivityIdMethod_should_invoke_getByActivityId_from_userActivityPendencyService() throws DataNotFoundException {
    userActivityPendencyFacade.getByActivityId(ACTIVITY_ID);
    verify(userActivityPendencyService, times(1)).getByActivityId(ACTIVITY_ID);
  }

  @Test (expected = HttpResponseException.class)
  public void getByActivityIdMethod_should_handle_DataNotFoundException_for_invalid_id() throws DataNotFoundException {
    when(userActivityPendencyService.getByActivityId("")).thenThrow(dataNotFoundException);
    userActivityPendencyFacade.getByActivityId("");
  }

  @Test
  public void listAllPendenciesMethod_should_invoke_listAllPendencies_from_userActivityPendencyService() throws DataNotFoundException, MemoryExcededException {
    userActivityPendencyFacade.listAllPendencies();
    verify(userActivityPendencyService, times(1)).listAllPendencies();
  }

  @Test (expected = HttpResponseException.class)
  public void listAllPendenciesMethod_should_handle_DataNotFoundException_in_case_of_no_pendency() throws DataNotFoundException, MemoryExcededException {
    when(userActivityPendencyService.listAllPendencies()).thenThrow(dataNotFoundException);
    userActivityPendencyFacade.listAllPendencies();
  }

  @Test (expected = HttpResponseException.class)
  public void listAllPendenciesMethod_should_handle_MemoryExcededException_in_case_of_many_pendencies() throws DataNotFoundException, MemoryExcededException {
    when(userActivityPendencyService.listAllPendencies()).thenThrow(memoryExcededException);
    userActivityPendencyFacade.listAllPendencies();
  }

  @Test
  public void listOpenedPendenciesMethod_should_invoke_listOpenedPendencies_from_userActivityPendencyService() throws DataNotFoundException, MemoryExcededException {
    userActivityPendencyFacade.listOpenedPendencies();
    verify(userActivityPendencyService, times(1)).listOpenedPendencies();
  }

  @Test (expected = HttpResponseException.class)
  public void listOpenedPendenciesMethod_should_handle_DataNotFoundException_in_case_of_no_pendency() throws DataNotFoundException, MemoryExcededException {
    when(userActivityPendencyService.listOpenedPendencies()).thenThrow(dataNotFoundException);
    userActivityPendencyFacade.listOpenedPendencies();
  }

  @Test (expected = HttpResponseException.class)
  public void listOpenedPendenciesMethod_should_handle_MemoryExcededException_in_case_of_many_pendencies() throws DataNotFoundException, MemoryExcededException {
    when(userActivityPendencyService.listOpenedPendencies()).thenThrow(memoryExcededException);
    userActivityPendencyFacade.listOpenedPendencies();
  }

  @Test
  public void listDonePendenciesMethod_should_invoke_listDonePendencies_from_userActivityPendencyService() throws DataNotFoundException, MemoryExcededException {
    userActivityPendencyFacade.listDonePendencies();
    verify(userActivityPendencyService, times(1)).listDonePendencies();
  }

  @Test (expected = HttpResponseException.class)
  public void listDonePendenciesMethod_should_handle_DataNotFoundException_in_case_of_no_pendency() throws DataNotFoundException, MemoryExcededException {
    when(userActivityPendencyService.listDonePendencies()).thenThrow(dataNotFoundException);
    userActivityPendencyFacade.listDonePendencies();
  }

  @Test (expected = HttpResponseException.class)
  public void listDonePendenciesMethod_should_handle_MemoryExcededException_in_case_of_many_pendencies() throws DataNotFoundException, MemoryExcededException {
    when(userActivityPendencyService.listDonePendencies()).thenThrow(memoryExcededException);
    userActivityPendencyFacade.listDonePendencies();
  }

}
