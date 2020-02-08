package br.org.otus.laboratory.configuration.collect.group;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantQualityControl;
import br.org.otus.laboratory.participant.ParticipantQualityControlDao;
import org.ccem.otus.participant.model.Participant;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.NoSuchElementException;

@Stateless
public class CollectGroupRaffle {

  @Inject
  private ParticipantQualityControlDao participantQualityControlDao;
  @Inject
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  // TODO: When the quality control group be, in fact, defined by a raffle, this
  // method can be refactored to be static, and the DAO used here can be
  // eliminated.
  public CollectGroupDescriptor perform(Participant participant) {
    LaboratoryConfiguration laboratoryConfiguration = this.laboratoryConfigurationDao.find();
    ParticipantQualityControl importedGroup = this.participantQualityControlDao.findParticipantGroup(participant.getRecruitmentNumber());
    if (importedGroup != null) {
      try {
        return laboratoryConfiguration.getCollectGroupConfiguration().getCollectGroupByName(importedGroup.getCode());
      } catch (NoSuchElementException e) {
        return new EmptyCollectorGroupDescriptor(importedGroup.getCode());
      }
    } else {
      return new NullableCollectGroupDescriptor();
    }
  }

}
