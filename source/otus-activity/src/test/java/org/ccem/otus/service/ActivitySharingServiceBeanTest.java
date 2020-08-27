package org.ccem.otus.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharingDto;
import org.ccem.otus.persistence.ActivitySharingDao;
import org.ccem.otus.service.sharing.ActivitySharingServiceBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
public class ActivitySharingServiceBeanTest {

  private static final String ACTIVITY_ID = "5a33cb4a28f10d1043710f7d";
  private static final ObjectId ACTIVITY_OID = new ObjectId(ACTIVITY_ID);

  @InjectMocks
  private ActivitySharingServiceBean activitySharingServiceBean;
  @Mock
  private ActivitySharingDao activitySharingDao;

  private ActivitySharing activitySharing;

  @Before
  public void setUp(){
    activitySharing = new ActivitySharing(ACTIVITY_OID, null, null);
  }

  @Test
  public void getSharedURL_method_should_return_url() throws DataNotFoundException {
    when(activitySharingDao.getSharedURL(ACTIVITY_OID)).thenReturn(activitySharing);
    assertNotNull(activitySharingServiceBean.getSharedURL(activitySharing));
  }

  @Test(expected = DataNotFoundException.class)
  public void getSharedURL_method_should_handle_DataNotFoundException_from_activitySharingDao_get_method() throws DataNotFoundException {
    when(activitySharingDao.getSharedURL(ACTIVITY_OID)).thenThrow(new DataNotFoundException());
    activitySharingServiceBean.getSharedURL(activitySharing);
  }

  
  @Test
  public void createSharedURL_method_should_return_url() {
    when(activitySharingDao.createSharedURL(activitySharing)).thenReturn(activitySharing);
    assertNotNull(activitySharingServiceBean.createSharedURL(activitySharing));
  }


  @Test
  public void renovateSharedURL_method_should_return_url() throws DataNotFoundException {
    ActivitySharing activitySharingRenovated = new ActivitySharing(ACTIVITY_OID, null, null);
    activitySharingRenovated.renovate();
    when(activitySharingDao.renovateSharedURL(activitySharing)).thenReturn(activitySharingRenovated);
    ActivitySharingDto activitySharingDtoRenovated = activitySharingServiceBean.renovateSharedURL(activitySharing);
    assertNotNull(activitySharingDtoRenovated);
    assertEquals(
      activitySharingRenovated.getExpirationDate(),
      activitySharingDtoRenovated.getActivitySharing().getExpirationDate());
    verify(activitySharingDao, Mockito.times(1)).renovateSharedURL(activitySharing);
  }

  @Test(expected = DataNotFoundException.class)
  public void renovateSharedURL_method_should_handle_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException()).when(activitySharingDao, "renovateSharedURL", activitySharing);
    activitySharingServiceBean.renovateSharedURL(activitySharing);
  }


  @Test
  public void deleteSharedURL_method_should_return_url() throws DataNotFoundException {
    activitySharingServiceBean.deleteSharedURL(ACTIVITY_ID);
    verify(activitySharingDao, Mockito.times(1)).deleteSharedURL(ACTIVITY_OID);
  }

  @Test(expected = DataNotFoundException.class)
  public void deleteSharedURL_method_should_handle_DataNotFoundException() throws Exception {
    doThrow(new DataNotFoundException()).when(activitySharingDao, "deleteSharedURL", ACTIVITY_OID);
    activitySharingServiceBean.deleteSharedURL(ACTIVITY_ID);
  }

}
