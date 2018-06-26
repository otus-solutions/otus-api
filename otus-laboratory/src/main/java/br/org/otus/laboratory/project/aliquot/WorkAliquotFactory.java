package br.org.otus.laboratory.project.aliquot;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

import java.util.ArrayList;
import java.util.List;

public class WorkAliquotFactory {

	public static List<WorkAliquot> getAliquotList(ParticipantLaboratoryDao participantLaboratoryDao,
			ParticipantDao participantDao) throws DataNotFoundException {
		List<WorkAliquot> aliquotList = new ArrayList<WorkAliquot>();

		//List<ParticipantLaboratory> participantList = participantLaboratoryDao.getAllParticipantLaboratory();
		List<ParticipantLaboratory> participantList = participantLaboratoryDao.getParticipantLaboratoryByDatePeriod();

		for (ParticipantLaboratory participantLaboratory : participantList) {
			Participant participant = participantDao.findByRecruitmentNumber(participantLaboratory.getRecruitmentNumber());

			participantLaboratory.getTubes().forEach(tube -> {
				tube.getAliquots().forEach(aliquot -> {
					WorkAliquot workAliquot;
					workAliquot = new WorkAliquot(aliquot, participant.getRecruitmentNumber(),
							participant.getBirthdate(), participant.getSex(), participant.getFieldCenter());
					aliquotList.add(workAliquot);
				});
			});

		}

		return aliquotList;
	}

}
