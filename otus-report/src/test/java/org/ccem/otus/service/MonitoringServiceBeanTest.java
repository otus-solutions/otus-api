package org.ccem.otus.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
//@PrepareForTest({ MonitoringServiceBean.class, MonitoringCenter.class })
public class MonitoringServiceBeanTest {

  private static final ArrayList<String> LIST_ACRONYMS_CENTERS = new ArrayList<>();
  private static final FieldCenter fieldCenter = new FieldCenter();
  private static final Long GOAL = (long) 3025L;
  
  @InjectMocks
  private MonitoringServiceBean monitoringServiceBean;

  @Mock
  private MonitoringCenter monitoringCenter;

  @Mock
  private FieldCenterDao fieldCenterDao;

  @Mock
  private ParticipantDao participantDao;

  @Before
  public void setUp() throws DataNotFoundException {
    
    fieldCenter.setAcronym("BA");
    fieldCenter.setName("Bahia");
    fieldCenter.setBackgroundColor("rgba(255, 99, 132, 0.2)");
    fieldCenter.setBorderColor("rgba(255, 99, 132, 1)");
    
    LIST_ACRONYMS_CENTERS.add(fieldCenter.getAcronym());
     when(fieldCenterDao.listAcronyms()).thenReturn(LIST_ACRONYMS_CENTERS);
     when(fieldCenterDao.fetchByAcronym(fieldCenter.getAcronym())).thenReturn(fieldCenter);
     when(participantDao.getPartipantsActives(fieldCenter.getAcronym())).thenReturn(GOAL);
  }

  @Test
  public void method_get_monitoring_centers_with_goals() throws ValidationException, DataNotFoundException {
    ArrayList<MonitoringCenter> response = monitoringServiceBean.getMonitoringCenter();
    assertTrue(response.size() > 0);
    assertEquals(fieldCenter.getName(), response.get(0).getName());
    assertEquals(fieldCenter.getBackgroundColor(), response.get(0).getBackgroundColor());
    assertEquals(fieldCenter.getBorderColor(), response.get(0).getBorderColor());
    assertEquals(GOAL, response.get(0).getGoal());
  }

}
