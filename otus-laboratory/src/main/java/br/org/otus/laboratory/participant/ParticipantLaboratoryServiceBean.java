package br.org.otus.laboratory.participant;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

import br.org.otus.laboratory.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.collect.group.CollectGroupRaffle;
import br.org.otus.laboratory.collect.tube.Tube;
import br.org.otus.laboratory.collect.tube.TubeService;
import br.org.otus.laboratory.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.dto.UpdateTubeAliquotsDTO;
import br.org.otus.laboratory.validators.AliquotUpdateValidateResponse;
import br.org.otus.laboratory.validators.AliquotUpdateValidator;
import br.org.otus.laboratory.validators.ParticipantLaboratoryValidator;

@Stateless
public class ParticipantLaboratoryServiceBean implements ParticipantLaboratoryService {

	@Inject
	private ParticipantLaboratoryDao participantLaboratoryDao;
	@Inject
	private ParticipantDao participantDao;
	@Inject
	private TubeService tubeService;
	@Inject
	private CollectGroupRaffle groupRaffle;

	private AliquotUpdateValidateResponse validate;

	@Override
	public boolean hasLaboratory(Long recruitmentNumber) {
		try {
			participantLaboratoryDao.findByRecruitmentNumber(recruitmentNumber);
			return true;
		} catch (DataNotFoundException e) {
			return false;
		}
	}

	@Override
	public ParticipantLaboratory getLaboratory(Long recruitmentNumber) throws DataNotFoundException {
		return participantLaboratoryDao.findByRecruitmentNumber(recruitmentNumber);
	}

	@Override
	public ParticipantLaboratory create(Long recruitmentNumber) throws DataNotFoundException {
		Participant participant = participantDao.findByRecruitmentNumber(recruitmentNumber);
		CollectGroupDescriptor collectGroup = groupRaffle.perform(participant);
		List<Tube> tubes = tubeService.generateTubes(TubeSeed.generate(participant, collectGroup));
		ParticipantLaboratory laboratory = new ParticipantLaboratory(recruitmentNumber, collectGroup.getName(), tubes);
		participantLaboratoryDao.persist(laboratory);
		return laboratory;
	}

	@Override
	public ParticipantLaboratory update(ParticipantLaboratory partipantLaboratory) throws DataNotFoundException {
		return participantLaboratoryDao.updateLaboratoryData(partipantLaboratory);
	}

	@Override
	public ParticipantLaboratory updateAliquots(UpdateAliquotsDTO updateAliquotsDTO) throws DataNotFoundException {
		ParticipantLaboratory participantLaboratory = participantLaboratoryDao.findByRecruitmentNumber(updateAliquotsDTO.getRecruitmentNumber());
		ParticipantLaboratoryValidator aliquotUpdateValidator = new AliquotUpdateValidator(updateAliquotsDTO, participantLaboratoryDao);

		try {
			validate = aliquotUpdateValidator.validate();

			for (UpdateTubeAliquotsDTO aliquotDTO : updateAliquotsDTO.getUpdateTubeAliquots()) {
				for (Tube tube : participantLaboratory.getTubes()) {
					if (tube.getCode().equals(aliquotDTO.getTubeCode())) {
						tube.setAliquots(aliquotDTO.getAliquots());
					}
				}
			}

			return update(participantLaboratory);
		} catch (ValidationException exception) {
			// TODO: ocorreu conflitos então prepara mensagem com erros!
			return null;
		}
	}

}
