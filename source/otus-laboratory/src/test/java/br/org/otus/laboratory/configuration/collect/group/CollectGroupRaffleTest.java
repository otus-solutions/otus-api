package br.org.otus.laboratory.configuration.collect.group;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantQualityControl;
import br.org.otus.laboratory.participant.ParticipantQualityControlDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class CollectGroupRaffleTest {

  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final String CODE = "000";
  private static final String COLLECT_GROUP_DESCRIPTOR_NAME = "collect_group";

  @InjectMocks
  private CollectGroupRaffle collectGroupRaffle;

  @Mock
  private ParticipantQualityControlDao participantQualityControlDao;
  @Mock
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  private LaboratoryConfiguration laboratoryConfiguration;
  private Participant participant;
  private ParticipantQualityControl participantQualityControl;
  private CollectGroupDescriptor collectGroupDescriptor;

  @Before
  public void setUp() throws DataNotFoundException {
    laboratoryConfiguration = PowerMockito.spy(new LaboratoryConfiguration());
    when(laboratoryConfigurationDao.find()).thenReturn(laboratoryConfiguration);
    participant = PowerMockito.spy(new Participant(RECRUITMENT_NUMBER));
    participantQualityControl = new ParticipantQualityControl(RECRUITMENT_NUMBER, COLLECT_GROUP_DESCRIPTOR_NAME);
    collectGroupDescriptor = PowerMockito.spy(new CollectGroupDescriptor(COLLECT_GROUP_DESCRIPTOR_NAME, "", new HashSet<>()));
  }

  @Test
  public void perform_method_should_throw_NullableCollectGroupDescriptor_in_case_participantQualityControlDao_return_null() throws DataNotFoundException {
    when(participantQualityControlDao.findParticipantGroup(RECRUITMENT_NUMBER)).thenReturn(null);
    assertTrue(collectGroupRaffle.perform(participant) instanceof NullableCollectGroupDescriptor);
  }

  @Test
  public void perform_method_should_return_collectGroupDescriptor() throws DataNotFoundException {
    CollectGroupConfiguration collectGroupConfiguration = PowerMockito.spy(new CollectGroupConfiguration(new HashSet<CollectGroupDescriptor>(){{
      add(collectGroupDescriptor);
    }}));
    when(participantQualityControlDao.findParticipantGroup(RECRUITMENT_NUMBER)).thenReturn(participantQualityControl);
    when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);
    assertEquals(collectGroupDescriptor, collectGroupRaffle.perform(participant));
  }

  @Test
  public void perform_method_should_return_EmptyCollectorGroupDescriptor_instance_in_case_empty_collectGroupConfiguration() throws DataNotFoundException {
    CollectGroupConfiguration collectGroupConfiguration = PowerMockito.spy(new CollectGroupConfiguration(new HashSet<>()));
    when(participantQualityControlDao.findParticipantGroup(RECRUITMENT_NUMBER)).thenReturn(participantQualityControl);
    when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);

    CollectGroupDescriptor emptyCollectorGroupDescriptor = collectGroupRaffle.perform(participant);
    assertTrue(emptyCollectorGroupDescriptor instanceof EmptyCollectorGroupDescriptor);
    assertTrue(emptyCollectorGroupDescriptor.getName().equals(COLLECT_GROUP_DESCRIPTOR_NAME));
  }

}
