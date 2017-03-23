package br.org.otus.laboratory.collect.group;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.participant.model.Participant;

import br.org.otus.laboratory.LaboratoryConfiguration;
import br.org.otus.laboratory.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantQualityControl;
import br.org.otus.laboratory.participant.ParticipantQualityControlDao;

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
		if(importedGroup != null) {
			return laboratoryConfiguration.getCollectGroupConfiguration().getCollectGroupByName(importedGroup.getCode());
		} else {
			return new NullableCollectGroupDescriptor();
		}
	}

}
