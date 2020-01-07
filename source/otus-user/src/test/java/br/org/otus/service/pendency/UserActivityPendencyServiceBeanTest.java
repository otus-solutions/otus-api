package br.org.otus.service.pendency;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.persistence.pendency.UserActivityPendencyDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.List;

@RunWith(PowerMockRunner.class)
public class UserActivityPendencyServiceBeanTest {

  private static final String USER_EMAIL = "requester@otus.com";
  private static final String PENDENCY_ID = "5e13997795818e14a91a5268";
  private static final ObjectId PENDENCY_OID = new ObjectId(PENDENCY_ID);
  private static final String ACTIVITY_ID = "5c7400d2d767afded0d84dcf";
  private static final ObjectId ACTIVITY_OID = new ObjectId(ACTIVITY_ID);

  @InjectMocks
  private UserActivityPendencyServiceBean userActivityPendencyServiceBean;
  @Mock
  private UserActivityPendencyDao userActivityPendencyDao;

  private UserActivityPendency userActivityPendency;
  private List<UserActivityPendency> userActivityPendencies;
  private String userActivityPendencyJson;

  @Before
  public void setUp(){
    userActivityPendency = new UserActivityPendency();
    userActivityPendencies = asList(userActivityPendency);
    userActivityPendencyJson = UserActivityPendency.serialize(userActivityPendency);
  }

  @Test
  public void createMethod_should_return_ID_in_success_case_persist(){
    when(userActivityPendencyDao.create(userActivityPendency)).thenReturn(PENDENCY_OID);
    assertEquals(PENDENCY_OID, userActivityPendencyServiceBean.create(USER_EMAIL, userActivityPendencyJson));
  }

  @Test
  public void updateMethod_should_invoke_update_from_userActivityPendencyDao() throws DataNotFoundException {
    userActivityPendencyServiceBean.update(PENDENCY_ID, userActivityPendencyJson);
    verify(userActivityPendencyDao, times(1)).update(PENDENCY_OID, userActivityPendency);
  }

  @Test (expected = DataNotFoundException.class)
  public void updateMethod_should_handle_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException()).when(userActivityPendencyDao, "update", PENDENCY_OID, userActivityPendency);
    userActivityPendencyServiceBean.update(PENDENCY_ID, userActivityPendencyJson);
  }

  @Test
  public void deleteMethod_should_invoke_from_userActivityPendencyDao() throws DataNotFoundException {
    userActivityPendencyServiceBean.delete(PENDENCY_ID);
    verify(userActivityPendencyDao, times(1)).delete(PENDENCY_OID);
  }

  @Test (expected = DataNotFoundException.class)
  public void deleteMethod_should_handle_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException()).when(userActivityPendencyDao, "delete", PENDENCY_OID);
    userActivityPendencyServiceBean.delete(PENDENCY_ID);
  }


  @Test
  public void getActivityByIdMethod_should_return_UserActivityPendency() throws DataNotFoundException {
    when(userActivityPendencyDao.findByActivityOID(ACTIVITY_OID)).thenReturn(userActivityPendency);
    assertEquals(userActivityPendency, userActivityPendencyServiceBean.getByActivityId(ACTIVITY_ID));
  }

  @Test (expected = DataNotFoundException.class)
  public void getActivityByIdMethod_should_handle_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException()).when(userActivityPendencyDao, "findByActivityOID", ACTIVITY_OID);
    userActivityPendencyServiceBean.getByActivityId(ACTIVITY_ID);
  }

  // list All
  @Test
  public void listAllPendencies_should_return_list_UserActivityPendency() throws MemoryExcededException, DataNotFoundException {
    when(userActivityPendencyDao.findAllPendencies(USER_EMAIL)).thenReturn(userActivityPendencies);
    assertEquals(userActivityPendencies, userActivityPendencyServiceBean.listAllPendencies(USER_EMAIL));
  }

  @Test (expected = DataNotFoundException.class)
  public void listAllPendencies_should_handle_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException()).when(userActivityPendencyDao, "findAllPendencies", USER_EMAIL);
    userActivityPendencyServiceBean.listAllPendencies(USER_EMAIL);
  }

  @Test (expected = MemoryExcededException.class)
  public void listAllPendencies_should_handle_MemoryExcededException() throws Exception {
    doThrow(new MemoryExcededException("")).when(userActivityPendencyDao, "findAllPendencies", USER_EMAIL);
    userActivityPendencyServiceBean.listAllPendencies(USER_EMAIL);
  }

  // list Opened
  @Test
  public void listOpenedPendencies_should_return_list_UserActivityPendency() throws MemoryExcededException, DataNotFoundException {
    when(userActivityPendencyDao.findOpenedPendencies(USER_EMAIL)).thenReturn(userActivityPendencies);
    assertEquals(userActivityPendencies, userActivityPendencyServiceBean.listOpenedPendencies(USER_EMAIL));
  }

  @Test (expected = DataNotFoundException.class)
  public void listOpenedPendencies_should_handle_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException()).when(userActivityPendencyDao, "findOpenedPendencies", USER_EMAIL);
    userActivityPendencyServiceBean.listOpenedPendencies(USER_EMAIL);
  }

  @Test (expected = MemoryExcededException.class)
  public void listOpenedPendencies_should_handle_MemoryExcededException() throws Exception {
    doThrow(new MemoryExcededException("")).when(userActivityPendencyDao, "findOpenedPendencies", USER_EMAIL);
    userActivityPendencyServiceBean.listOpenedPendencies(USER_EMAIL);
  }

  // list Done
  @Test
  public void listDonePendencies_should_return_list_UserActivityPendency() throws MemoryExcededException, DataNotFoundException {
    when(userActivityPendencyDao.findDonePendencies(USER_EMAIL)).thenReturn(userActivityPendencies);
    assertEquals(userActivityPendencies, userActivityPendencyServiceBean.listDonePendencies(USER_EMAIL));
  }

  @Test (expected = DataNotFoundException.class)
  public void listDonePendencies_should_handle_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException()).when(userActivityPendencyDao, "findDonePendencies", USER_EMAIL);
    userActivityPendencyServiceBean.listDonePendencies(USER_EMAIL);
  }

  @Test (expected = MemoryExcededException.class)
  public void listDonePendencies_should_handle_MemoryExcededException() throws Exception {
    doThrow(new MemoryExcededException("")).when(userActivityPendencyDao, "findDonePendencies", USER_EMAIL);
    userActivityPendencyServiceBean.listDonePendencies(USER_EMAIL);
  }

}
